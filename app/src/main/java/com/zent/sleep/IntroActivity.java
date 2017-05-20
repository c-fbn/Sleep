package com.zent.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zent.sleep.intro.HelloFragment;
import com.zent.sleep.intro.ScheduleFragment;

import agency.tango.materialintroscreen.MaterialIntroActivity;

/**
 * Created by Fabian Choi on 5/19/2017.
 * Intro screen using Material Intro Library
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new HelloFragment());
        addSlide(new ScheduleFragment());
    }
}
