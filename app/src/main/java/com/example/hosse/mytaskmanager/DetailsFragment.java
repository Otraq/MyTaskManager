package com.example.hosse.mytaskmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

public class DetailsFragment extends Fragment {


    private Note note;
    private TextView dscText;
    private TextView dateText;
    private TextView timeText;
    private Button editBtn;
    private Button deleteBtn;
    private Button doneBtn;
    private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        note = NoteList.getInstance().findNote((UUID) getActivity().getIntent().getSerializableExtra("NoteID"));
        dscText = view.findViewById(R.id.des_text);
        dateText = view.findViewById(R.id.date_text);
        timeText = view.findViewById(R.id.time_text);
        editBtn = view.findViewById(R.id.edit_btn);
        deleteBtn = view.findViewById(R.id.delete_btn);
        doneBtn = view.findViewById(R.id.done_btn);
        toolbar=view.findViewById(R.id.details_toolbar);

        dscText.setText(note.getDescription());
        dateText.setText(MyDate.getStringDate(note.getDateTime()));
        timeText.setText(MyDate.getStringTime(note.getDateTime()));
        toolbar.setTitle(note.getTitle());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NoteList.getInstance().getNoteList().remove(NoteList.getInstance().findNote(note.getId()));
                        Snackbar.make(getView(),"You've deleted this task.",Snackbar.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setMessage("Do you want to delete this note?");
                dialog.show();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setDone(true);
                Snackbar.make(view,"You've done this task.",Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
