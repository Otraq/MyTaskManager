package com.example.hosse.mytaskmanager;

import com.example.hosse.mytaskmanager.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class NoteList {

    private List<Note> noteList;
    private static final NoteList ourInstance = new NoteList();
    public static DatabaseHelper db;

    static NoteList getInstance() {
        return ourInstance;
    }

    private NoteList() {
        noteList = new ArrayList<>();
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public Note findNote(int id) {
        for (Note note : noteList) {
            if (note.getId() == id) {
                return note;
            }
        }

        return null;
    }

    public static List<Note> getNewNoteList(int mode) {
        List<Note> newList = new ArrayList<>();
        if (mode == 1) {
            return NoteList.getInstance().getNoteList();
        } else if (mode == 2) {
            for (Note note : NoteList.getInstance().getNoteList()) {
                if (note.getDone())
                    newList.add(note);
            }
        } else {
            for (Note note : NoteList.getInstance().getNoteList()) {
                if (!note.getDone())
                    newList.add(note);
            }
        }

        return newList;
    }
}
