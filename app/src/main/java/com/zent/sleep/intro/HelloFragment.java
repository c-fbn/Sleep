package com.zent.sleep.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.zent.sleep.R;

/**
 * Created by Fabian Choi on 5/20/2017.
 */

public class HelloFragment extends SlideFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.intro_hello,
                container, false);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
