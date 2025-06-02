package com.example.dailyplanner;

import java.io.Serializable;

public class Event implements Serializable {
    public String title;
    public long startTime; // millis since epoch
    public int duration;   // in minutes
    public int reminderTime; // in minutes before start

    public Event(String title, long startTime, int duration, int reminderTime) {
        this.title = title;
        this.startTime = startTime;
        this.duration = duration;
        this.reminderTime = reminderTime;
    }
}
