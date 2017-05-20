package com.zent.sleep.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zent.sleep.R;

import agency.tango.materialintroscreen.SlideFragment;

/**
 * Created by Fabian Choi on 5/19/2017.
 * (2) Get user's schedule
 */

public class ScheduleFragment extends SlideFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Get rid of notification bar
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View view = inflater.inflate(R.layout.intro_hello, container, false);

        return view;
    }

    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "no";
        //return getString(R.string.error_message);
    }
}