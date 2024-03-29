package com.zent.sleep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.zent.sleep.model.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get rid of notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        // Start Realm
        Realm.init(getBaseContext());

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Start realm
                Realm realm = Realm.getInstance(com.zent.sleep.Realm.getConfig());

                /* Check if new user with database existence */
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                // Start intro activity if no user exists
                if(realm.where(User.class).findAll().size() == 0) {
                    // Delayed start for MainActivity
                    intent.putExtra("start", false);
                    SplashActivity.this.startActivity(intent);

                    Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                } else {
                    intent.putExtra("start", true);
                    SplashActivity.this.startActivity(intent);
                }

                overridePendingTransition(0, R.transition.splash_fade_out);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
