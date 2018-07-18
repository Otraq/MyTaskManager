package com.example.hosse.mytaskmanager;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String ARG_TIME = "arg_time";
    public static final String EXTRA_TIME = "extra_time";

    private Date mDate;
    private TimePicker mTimePicker;
    private int hour;
    private int minute;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = (Date) getArguments().getSerializable(ARG_TIME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_time_picker, null);
        mTimePicker = view.findViewById(R.id.time_picker);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("TimePicker")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult();
                    }
                }).create();

        return dialog;
    }

    private void sendResult() {
        if (getTargetFragment() == null)
            return;

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(mDate);

        int h = mTimePicker.getCurrentHour();
        int m = mTimePicker.getCurrentMinute();

        mDate.setHours(h);
        mDate.setMinutes(m);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
