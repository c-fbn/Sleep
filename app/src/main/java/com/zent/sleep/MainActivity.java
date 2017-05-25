package com.zent.sleep;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.zent.sleep.model.Settings;
import com.zent.sleep.model.User;
import com.zent.sleep.receiver.NoticeReceiver;
import com.zent.sleep.service.SleepService;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button) Button goToSleepActivity;

    private boolean delayedStart;
    private boolean started = false;

    private Realm realm; // Realm instance
    private User user;
    private Settings settings;

    private AlarmManager alarmManager;
    private PendingIntent pendingNoticeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        delayedStart = !getIntent().getBooleanExtra("start", true);
        if(!delayedStart) {
            started = true;
            start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logic to deal with Splash screen and delaying the start
        if(delayedStart)
            delayedStart = false;
        else {
            if(!started) {
                started = true;
                start();
            }
        }
    }

    public void start() {
        Logger.d("Started");

        /* Instantiate vars */
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        realm = Realm.getInstance(com.zent.sleep.Realm.getConfig());

        // Get user
        user = realm.where(User.class).findFirst();

        // Get settings
        if(realm.where(Settings.class).findAll().size() == 0) {
            // Create settings
            realm.beginTransaction();
            settings = realm.createObject(Settings.class);
            settings.setEnabled(true);
            settings.setNightMode(true);
            realm.commitTransaction();

            // Set up alarms
            setAlarms();
        } else {
            settings = realm.where(Settings.class).findFirst();
        }
    }

    @OnClick(R.id.button)
    public void onClickSleepActivity() {
        enableAlarm(false);
        enableAlarm(true);
    }

    /**
     * Enable the alarm given parameter
     * @param enable Value to set the enabled state of the alarm
     */
    private void enableAlarm(boolean enable) {
        Logger.d("Set alarm to " + enable);

        if(settings.isEnabled() != enable) {
            // Update values
            realm.beginTransaction();
            settings.setEnabled(enable);
            realm.commitTransaction();

            // Update alarms
            if(enable)
                setAlarms();
            else
                alarmManager.cancel(pendingNoticeIntent);
        }
    }

    private void setAlarms() {
        Logger.d("Alarms set");

        // Notify user of upcoming sleep schedule
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, user.getStartSleepHour());
        calendar.set(Calendar.MINUTE, user.getStartSleepMinute());
        calendar.add(Calendar.MINUTE, -1 * SleepService.WAIT_BEGINNING);
        Intent noticeIntent = new Intent(MainActivity.this, NoticeReceiver.class);
        pendingNoticeIntent = PendingIntent.getBroadcast(MainActivity.this, 0, noticeIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingNoticeIntent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
