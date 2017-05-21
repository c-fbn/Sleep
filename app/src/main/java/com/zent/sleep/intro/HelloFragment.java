package com.zent.sleep.intro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getkeepsafe.relinker.ReLinker;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.heinrichreimersoftware.materialintro.slide.Slide;
import com.orhanobut.logger.Logger;
import com.zent.sleep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fabian Choi on 5/20/2017.
 */

public class HelloFragment extends SlideFragment {

    public HelloFragment() {

    }

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
