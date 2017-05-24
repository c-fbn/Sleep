package com.zent.sleep.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.zent.sleep.R;


/**
 * Created by Fabian Choi on 5/23/2017.
 *
 */
public class SleepService extends IntentService {
    public static final String NOTIFICATION = "com.zent.sleep.service";

    private boolean isWaiting;

    public SleepService() {
        super("SleepService");
    }

    public SleepService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Logger.d("SleepService started");

        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert_1);
        mediaPlayer.setLooping(true);

        Handler handler = new Handler();
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        };
        AudioManager audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 20, 0);
        handler.postDelayed(backgroundPlayback, 5 * 1000);
        mediaPlayer.start();
    }
}
