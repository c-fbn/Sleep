package com.zent.sleep.intro;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.zent.sleep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fabian Choi on 5/20/2017.
 */

public class NameFragment extends SlideFragment {
    @BindView(R.id.introNameEditText) EditText input;

    public NameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.intro_name,
                container, false);
        ButterKnife.bind(this, rootView);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateNavigation();
            }
        });

        return rootView;
    }

    @Override
    public boolean canGoForward() {
        return !input.getText().toString().equals("");
    }

    @Override
    public boolean canGoBackward() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
