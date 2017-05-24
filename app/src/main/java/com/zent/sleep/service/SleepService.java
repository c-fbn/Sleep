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
import com.zent.sleep.model.Settings;
import com.zent.sleep.model.User;

import io.realm.Realm;


/**
 * Created by Fabian Choi on 5/23/2017.
 * Sleep service. Does the actions
 */
public class SleepService extends IntentService {
    public static final String NOTIFICATION = "com.zent.sleep.service";

    private MediaPlayer mediaPlayer;

    public SleepService() {
        super("SleepService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Logger.d("SleepService started");

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

    private void startBackgroundMusic() {
        changeActivityText("Playing background music", "Relax and sleep");

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_jungle);

        Handler handler = new Handler();
        // TODO: Check warning
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        };
        handler.postDelayed(backgroundPlayback, 10 * 1000);
        mediaPlayer.start();
    }

    private void startBreathingHelper() {
        Handler handler = new Handler();
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                playBeep(false, 0);
                playBeep(false, 1);
                playBeep(false, 2);
            }
        };
        handler.postDelayed(backgroundPlayback, 2 * 1000);
    }

    private void playBeep(final boolean isTransitionBeep, int delayInSeconds) {
        Handler handler = new Handler();
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                if(isTransitionBeep)
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_1);
                else
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_1);
                mediaPlayer.start();
            }
        };
        handler.postDelayed(backgroundPlayback, delayInSeconds * 1000);
    }

    private void changeActivityText(String title, String subtitle) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("title", title);
        intent.putExtra("subtitle", subtitle);
        sendBroadcast(intent);
    }
}
