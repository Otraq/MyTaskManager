package com.example.hosse.mytaskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hosse.mytaskmanager.database.DatabaseHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddDialog extends DialogFragment {


    private Button addBtn;
    private Button dismissBtn;
    private Button datePickerBtn;
    private Button timePickerBtn;
    private Date mDate;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private DatabaseHelper db;
    private Note note;
    private int m;

    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    public static final String ARGS_M = "args_m";
    public static final String ARGS_NOTE = "args_note";
    public static final String SAVED_VALUE = "saved_value";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int noteId = getArguments().getInt(ARGS_NOTE);
        note = NoteList.getInstance().findNote(noteId);
        m = getArguments().getInt(ARGS_M);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add, container, false);

        addBtn = view.findViewById(R.id.add_dialog_btn);
        dismissBtn = view.findViewById(R.id.dismiss_dialog_btn);
        datePickerBtn = view.findViewById(R.id.date_picker_btn);
        timePickerBtn = view.findViewById(R.id.time_picker_btn);
        titleEditText = view.findViewById(R.id.title_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        db = new DatabaseHelper(getActivity());

        mDate = new Date();
        if (m == DetailsFragment.M) {
            mDate = note.getDateTime();
            datePickerBtn.setText(MyDate.getStringDate(mDate));
            timePickerBtn.setText(MyDate.getStringTime(mDate));
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
        }

        if (savedInstanceState != null) {
            mDate = (Date) savedInstanceState.getSerializable(SAVED_VALUE);
            datePickerBtn.setText(MyDate.getStringDate(mDate));
            timePickerBtn.setText(MyDate.getStringTime(mDate));
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (m == DetailsFragment.M) {

                    
                }

                long id = db.insertNote(titleEditText.getText().toString(), descriptionEditText.getText().toString(), mDate);

                Note note = new Note((int) (long) id);
                note.setTitle(titleEditText.getText().toString());
                note.setDescription(descriptionEditText.getText().toString());
                note.setDateTime(mDate);
                note.setDone(false);
                NoteList.getInstance().getNoteList().add(note);


                for (int i = 0; i < 3; i++) {
                    ListFragment listFragment = (ListFragment) getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + i);
                    listFragment.noteAdapter.notifyDataSetChanged();
                }
                dismiss();
            }
        });
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mDate);
                datePickerFragment.setTargetFragment(AddDialog.this, REQUEST_DATE);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerDialog");
            }
        });

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mDate);
                timePickerFragment.setTargetFragment(AddDialog.this, REQUEST_TIME);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), "TimePickerDialog");
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DATE) {
            if (data == null)
                return;

            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDate.setTime(date.getTime());

            datePickerBtn.setText(MyDate.getStringDate(date));
        } else if (requestCode == REQUEST_TIME) {
            if (data == null)
                return;
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mDate.setTime(date.getTime());
            timePickerBtn.setText(MyDate.getStringTime(date));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_VALUE, mDate);
    }

    public static AddDialog newInstance(int m, int noteId) {

        Bundle args = new Bundle();
        args.putInt(ARGS_M, m);
        args.putInt(ARGS_NOTE, noteId);
        AddDialog fragment = new AddDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void update() {
        Note updatedNote = new Note(note.getId());
        updatedNote.setTitle(titleEditText.getText().toString());
        updatedNote.setDescription(descriptionEditText.getText().toString());
        updatedNote.setDateTime(mDate);
        updatedNote.setDone(note.getDone());

        db.updateNote(updatedNote);
        NoteList.getInstance().setNoteList(db.getNoteList());

    }
}
