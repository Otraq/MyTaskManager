package com.example.hosse.mytaskmanager;

import java.util.Date;

public class MyDate {

    private static String date;

    private MyDate() {
    }

    public static String getStringDate(Date usreDate) {
        date = usreDate.toString();
        return date.substring(0, 10) + " " + date.substring(30);
    }

    public static String getStringTime(Date userDate) {
        date = userDate.toString();
        return date.substring(11, 18);
    }
}
