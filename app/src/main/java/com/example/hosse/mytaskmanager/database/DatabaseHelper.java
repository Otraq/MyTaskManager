package com.example.hosse.mytaskmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hosse.mytaskmanager.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertNote(String title, String dsc, Date dateTime) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TITLE, title);
        values.put(Note.COLUMN_DSC, dsc);
        values.put(Note.COLUMN_TIMESTAMP, dateTime.getTime());
        values.put(Note.COLUMN_ISDONE, 0);

        long id = sqLiteDatabase.insert(Note.TABLE_NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_TITLE, Note.COLUMN_DSC, Note.COLUMN_TIMESTAMP, Note.COLUMN_ISDONE},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE)));
        note.setDescription(cursor.getString(cursor.getColumnIndex(Note.COLUMN_DSC)));
        Date date = new Date();
        date.setTime(cursor.getLong(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
        note.setDateTime(date);
        int done = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ISDONE));
        if (done == 0)
            note.setDone(false);
        else
            note.setDone(true);

        return note;
    }

    public List<Note> getNoteList() {

        List<Note> notes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(Note.COLUMN_DSC)));
                Date date = new Date();
                date.setTime(cursor.getLong(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setDateTime(date);
                int done = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ISDONE));
                if (done == 0)
                    note.setDone(false);
                else
                    note.setDone(true);

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TITLE, note.getTitle());
        values.put(Note.COLUMN_DSC, note.getDescription());
        values.put(Note.COLUMN_TIMESTAMP, note.getDateTime().getTime());
        if (note.getDone())
            values.put(Note.COLUMN_ISDONE, 1);
        else
            values.put(Note.COLUMN_ISDONE, 0);
        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }


}
