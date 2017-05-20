package com.zent.sleep;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.zent.sleep.intro.HelloFragment;
import com.zent.sleep.intro.ScheduleFragment;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

/**
 * Created by Fabian Choi on 5/19/2017.
 * Intro screen using Material Intro Library
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorGreen)
                .buttonsColor(R.color.colorGreen)
                .title("Want more?")
                .description("Go on")
                .build());

        addSlide(new HelloFragment());
        addSlide(new ScheduleFragment());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Toast.makeText(this, "Try this library in your project! :)", Toast.LENGTH_SHORT).show();
    }
}
