package com.zent.sleep.intro;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.zent.sleep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fabian Choi on 5/20/2017.
 */

public class ScheduleFragment extends SlideFragment {
    @BindView(R.id.introScheduleTextView) TextView text;
    @BindView(R.id.introScheduleStartEditText) EditText startInput;
    @BindView(R.id.introScheduleEndEditText) EditText endInput;
    private int startHour, startMinutes, endHour, endMinutes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.intro_schedule,
                container, false);
        ButterKnife.bind(this, rootView);

        // Time pickers for both text edits
        startInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        // TODO: Polish this to add AM/PM and display as 00 if minutes == 0
                        startInput.setText(selectedHour + ":" + selectedMinute);
                        startHour = selectedHour;
                        startMinutes = selectedMinute;
                    }
                }, 22, 0, false);
                timePickerDialog.setTitle("Select Start Time");
                timePickerDialog.show();
            }
        });

        endInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endInput.setText(selectedHour + ":" + selectedMinute);
                        endHour = selectedHour;
                        endMinutes = selectedMinute;
                    }
                }, 8, 0, false);
                timePickerDialog.setTitle("Select End Time");
                timePickerDialog.show();
            }
        });

        // Update requirements for next slide
        startInput.addTextChangedListener(new TextWatcher() {
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

        endInput.addTextChangedListener(new TextWatcher() {
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
        return !startInput.getText().toString().equals("") &&
                !startInput.getText().toString().equals("");
    }

    @Override
    public boolean canGoBackward() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public int getStartHour() { return startHour; }

    public int getStartMinutes() {
        return startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setName(String name) {
        if(text != null)
            text.setText("Okay " + name + ", now set up a sleep schedule.");
    }
}
