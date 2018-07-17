package com.example.hosse.mytaskmanager;

import java.util.Date;
import java.util.UUID;

public class Note {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DSC = "description";
    public static final String COLUMN_TIMESTAMP = "dateTime";
    public static final String COLUMN_ISDONE = "done";

    private int id;
    private String title;
    private String description;
    private Date dateTime;
    private Boolean done;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_DSC + " TEXT,"
            + COLUMN_ISDONE + " INTEGER,"
            + COLUMN_TIMESTAMP + " INTEGER"
            + ")";

    public Note(int id) {
        this.id = id;
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
