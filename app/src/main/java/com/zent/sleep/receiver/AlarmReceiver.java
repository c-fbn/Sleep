package com.zent.sleep.receiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.orhanobut.logger.Logger;
import com.zent.sleep.R;
import com.zent.sleep.SleepActivity;
import com.zent.sleep.service.SleepService;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Fabian Choi on 5/23/2017.
 * Wakes user up
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Logger.d("AlarmReceiver received");

        // Ringtone
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mp = MediaPlayer.create(context, uri);
        mp.setLooping(true);
        mp.start();

        // Send notification
        NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, SleepActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        // TODO: Implement notification actions
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Good morning!")
                .setContentText("It's time to wake up")
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentIntent(contentIntent)
                .addAction(R.drawable.mi_ic_finish, "Turn off", contentIntent)
                .build();

        SleepService.NOTIFICATION_ID = (int)((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        nm.notify(SleepService.NOTIFICATION_ID , notification);

        setResultCode(Activity.RESULT_OK);
    }
}
