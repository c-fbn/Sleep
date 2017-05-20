package com.zent.sleep;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepActivity extends Activity {
    @BindView(R.id.beep) Button beepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.beep)
    public void Beep() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bg_rain);
        mediaPlayer.setLooping(true);

        Handler handler = new Handler();
        Runnable backgroundPlayback = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        };
        final float volume = (float) (1 - (Math.log(0) / Math.log(100)));

        mediaPlayer.setVolume(volume, volume);
        handler.postDelayed(backgroundPlayback, 10 * 1000);
        mediaPlayer.start();
    }

}
