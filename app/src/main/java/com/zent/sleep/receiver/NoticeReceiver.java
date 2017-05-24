package com.zent.sleep.receiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.orhanobut.logger.Logger;
import com.zent.sleep.MainActivity;
import com.zent.sleep.R;
import com.zent.sleep.SleepActivity;
import com.zent.sleep.service.SleepService;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Fabian Choi on 5/23/2017.
 *
 */
public class NoticeReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Logger.d("NoticeReceiver received");

        // Ringtone
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alert_1);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 20, 0);
        mediaPlayer.start();

        // Notification
        NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, SleepActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Get ready to sleep!")
                .setContentText("In 15 minutes, I will help you to sleep.")
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentIntent(contentIntent)
                .addAction(R.drawable.mi_ic_next, "Start now", contentIntent)
                .addAction(R.drawable.mi_ic_skip, "Not today", contentIntent).build();

        nm.notify((int)System.currentTimeMillis(), notification);

        // Delayed start of service
        Handler handler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                // Start sleep service
                ComponentName comp = new ComponentName(context.getPackageName(),
                        SleepService.class.getName());
                startWakefulService(context, (intent.setComponent(comp)));
            }
        };
        handler.postDelayed(action, 5 * 1000);

        setResultCode(Activity.RESULT_OK);
    }
}
