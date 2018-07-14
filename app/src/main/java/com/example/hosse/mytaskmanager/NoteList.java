package com.example.hosse.mytaskmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class NoteList {

    private List<Note> noteList;
    private static final NoteList ourInstance = new NoteList();

    static NoteList getInstance() {
        return ourInstance;
    }

    private NoteList() {
        noteList = new ArrayList<>();
        Note note1 = new Note(UUID.randomUUID());
        note1.setTitle("Title1");
        note1.setDescription("This is a text!");
        note1.setDateTime(new Date());
        note1.setDone(false);

        Note note2 = new Note(UUID.randomUUID());
        note2.setTitle("Title2");
        note2.setDescription("This is another text!");
        note2.setDateTime(new Date());
        note2.setDone(false);

        Note note3 = new Note(UUID.randomUUID());
        note3.setTitle("Title3");
        note3.setDescription("Hey You!");
        note3.setDateTime(new Date());
        note3.setDone(false);

        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public Note findNote(UUID id) {
        for (Note note : noteList) {
            if (note.getId().equals(id)) {
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
