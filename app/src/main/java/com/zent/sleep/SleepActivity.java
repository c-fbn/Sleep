package com.zent.sleep;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zent.sleep.service.SleepService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepActivity extends Activity {
    @BindView(R.id.sleepTextView) TextView titleView;
    @BindView(R.id.sleepSubTextView) TextView subtitleView;
    @BindView(R.id.sleepProgressBar) ProgressBar progressBar;

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        ButterKnife.bind(this);

        backPressed = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if(!backPressed) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to exit? Your morning alarm will also be disabled!\nPress back again to exit.");
            alertDialogBuilder.show();
            backPressed = true;
        } else {
            // Cancel all actions
            SleepService.cancelled = true;
            stopService(new Intent(getBaseContext(), SleepService.class));
            super.onBackPressed();
        }
    }
}
