package com.zent.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.zent.sleep.intro.CustomizeFragment;
import com.zent.sleep.intro.FinishFragment;
import com.zent.sleep.intro.HelloFragment;
import com.zent.sleep.intro.NameFragment;
import com.zent.sleep.intro.ScheduleFragment;
import com.zent.sleep.model.User;

import io.realm.Realm;

/**
 * Created by Fabian Choi on 5/19/2017.
 * Intro screen using Material Intro Library
 */

public class IntroActivity extends com.heinrichreimersoftware.materialintro.app.IntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(true);

        /* Fragments */
        HelloFragment helloFragment = new HelloFragment();
        final NameFragment nameFragment = new NameFragment();
        final ScheduleFragment scheduleFragment = new ScheduleFragment();
        final CustomizeFragment customizeFragment = new CustomizeFragment();
        FinishFragment finishFragment = new FinishFragment();

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorNight)
                .backgroundDark(R.color.colorNight)
                .fragment(helloFragment)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorCyan)
                .backgroundDark(R.color.colorCyan)
                .fragment(nameFragment)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorDeepPurple)
                .backgroundDark(R.color.colorDeepPurple)
                .fragment(scheduleFragment)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorIndigo)
                .backgroundDark(R.color.colorIndigo)
                .fragment(customizeFragment)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorGreen)
                .backgroundDark(R.color.colorGreen)
                .fragment(finishFragment)
                .build());

        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override
            public void onNavigationBlocked(int i, int i1) {
                Toast.makeText(getApplicationContext(), "All fields are required!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 2:
                        // Hide keyboard
                        InputMethodManager imm = (InputMethodManager)getSystemService(getApplication().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getContentView().getWindowToken(), 0);

                        scheduleFragment.setName(nameFragment.getName());
                        break;
                    case 4:
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        User user = realm.createObject(User.class);
                        user.setName(nameFragment.getName());
                        user.setStartSleepHour(scheduleFragment.getStartHour());
                        user.setStartSleepMinute(scheduleFragment.getStartMinutes());
                        user.setEndSleepHour(scheduleFragment.getEndHour());
                        user.setEndSleepMinute(scheduleFragment.getEndMinutes());
                        user.setBackgroundMusicEnabled(customizeFragment.isBgMusicChecked());
                        user.setBreathingEnabled(customizeFragment.isBreathingChecked());
                        realm.commitTransaction();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
