package com.zent.sleep.intro;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.zent.sleep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fabian Choi on 5/22/2017.
 */

public class CustomizeFragment  extends SlideFragment {
    @BindView(R.id.introCustomizeHelpButton) ImageButton helpButton;
    @BindView(R.id.introCustomizeBackgroundCheckBox) CheckBox bgmusic;
    @BindView(R.id.introCustomizeBreathingCheckBox) CheckBox breathing;
    private boolean bgMusicChecked = true;
    private boolean breathingChecked = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.intro_customize,
                container, false);
        ButterKnife.bind(this, rootView);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Help")
                        .setMessage("Background music: \n\n4-7-8 Breathing beats:")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_help)
                        .show();
            }
        });

        bgmusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bgMusicChecked = b;
            }
        });

        breathing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                breathingChecked = b;
            }
        });

        return rootView;
    }

    @Override
    public boolean canGoForward() {
        return true;
    }

    @Override
    public boolean canGoBackward() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isBgMusicChecked() { return bgMusicChecked; }
    public boolean isBreathingChecked() { return breathingChecked; }
}
