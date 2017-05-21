package com.zent.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.Slide;
import com.orhanobut.logger.Logger;
import com.zent.sleep.intro.HelloFragment;
import com.zent.sleep.intro.NameFragment;

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
        NameFragment nameFragment = new NameFragment();

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorNight)
                .backgroundDark(R.color.colorNight)
                .fragment(helloFragment)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorBlue)
                .backgroundDark(R.color.colorBlue)
                .fragment(nameFragment)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorDeepPurple)
                .backgroundDark(R.color.colorDeepPurple)
                .fragment(R.layout.intro_schedule)
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
                // Hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(getApplication().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getContentView().getWindowToken(), 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
