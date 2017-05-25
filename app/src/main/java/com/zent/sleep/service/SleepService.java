package com.zent.sleep.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.zent.sleep.MainActivity;
import com.zent.sleep.R;
import com.zent.sleep.SleepActivity;
import com.zent.sleep.model.Settings;
import com.zent.sleep.model.User;
import com.zent.sleep.receiver.AlarmReceiver;
import com.zent.sleep.receiver.NoticeReceiver;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;


/**
 * Created by Fabian Choi on 5/23/2017.
 * Sleep service. Does the actions
 */
public class SleepService extends Service {
    public static final String NOTIFICATION = "com.zent.sleep";
    public static int NOTIFICATION_ID = 0;

    // TODO: Implement these as settings
    // In minutes
    public static final int WAIT_BEGINNING = 15;
    public static final int SERVICE_DURATION = 30;
    public static final int WAIT_BEATS_START = 1;

    private MediaPlayer mediaPlayer;
    private MediaPlayer beatsPlayer;
    private ArrayList<Integer> beats;   // TODO: implement as queue

    public static boolean cancelled = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("SleepService started");
        cancelled = false;
        Handler handler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                beats = new ArrayList<>();

                // Cancel notification
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(SleepService.NOTIFICATION_ID);

                // Prepare media player
                mediaPlayer = new MediaPlayer();
                AudioManager audioManager = (AudioManager) getApplicationContext().
                        getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 20, 0);

                // Get user settings
                Realm realm = io.realm.Realm.getInstance(com.zent.sleep.Realm.getConfig());
                User user = realm.where(User.class).findFirst();

                // Play background music
                if(user.isBackgroundMusicEnabled()) {
                    startBackgroundMusic();
                }

                // Play breathing helper
                if(user.isBreathingEnabled()) {
                    startBreathingHelper();
                }
            }
        };
        handler.postDelayed(action, WAIT_BEGINNING * 60 * 1000);
        return START_STICKY;
    }

    private void startBackgroundMusic() {
        Logger.d("Starting background music");
        changeActivityText("Playing background music", "Relax and sleep");

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_jungle);

        Handler handler = new Handler();
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                stopService(new Intent(getBaseContext(), SleepService.class));
            }
        };
        handler.postDelayed(backgroundPlayback, SERVICE_DURATION * 60 * 1000);
        mediaPlayer.start();
    }

    private void startBreathingHelper() {
        Logger.d("Starting breathing helper");

        prepareBreathingCycle();

        Handler handler = new Handler();
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                playBeep(); // Start
            }
        };
        handler.postDelayed(backgroundPlayback, WAIT_BEATS_START * 60 * 1000);
    }

    private void playBeep() {
        if(beats.size() > 0) {
            Logger.d("Beep");
            // Play
            beatsPlayer = MediaPlayer.create(getApplicationContext(), beats.get(0));
            beats.remove(0);
            Handler handler = new Handler();
            Runnable stopPlayback = new Runnable() {
                @Override
                public void run() {
                    if(beatsPlayer != null){
                        beatsPlayer.stop();
                        playBeep();
                    }
                }
            };
            handler.postDelayed(stopPlayback, 1000);
            beatsPlayer.start();
        } else {
            prepareBreathingCycle();
            playBeep();
        }
    }

    private void prepareBreathingCycle() {
        beats.add(R.raw.beep_1);
        beats.add(R.raw.beep_1);
        beats.add(R.raw.beep_1);
        beats.add(R.raw.beep_2);
        beats.add(R.raw.beep_2);
        beats.add(R.raw.beep_2);
        beats.add(R.raw.beep_2);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_3);
        beats.add(R.raw.beep_4);
        beats.add(R.raw.beep_4);
        beats.add(R.raw.beep_4);
        beats.add(R.raw.beep_4);
        beats.add(R.raw.beep_4);
        beats.add(R.raw.beep_4);
        beats.add(R.raw.beep_4);
    }

    private void changeActivityText(String title, String subtitle) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("title", title);
        intent.putExtra("subtitle", subtitle);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        if(!cancelled) {
            Logger.d("Creating alarm");
            // Create alarm for the morning
            Intent noticeIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent pendingNoticeIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, noticeIntent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE) + 1);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingNoticeIntent);
        }

        // Release resources
        mediaPlayer.stop();
        mediaPlayer.release();
        if(beatsPlayer.isPlaying())
            beatsPlayer.stop();
        beatsPlayer.release();
        mediaPlayer = null;
        beatsPlayer = null;

        super.onDestroy();
    }
}
