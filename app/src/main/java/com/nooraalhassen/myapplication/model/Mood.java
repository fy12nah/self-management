package com.nooraalhassen.myapplication.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nooraalhassen on 3/30/16.
 */
public class Mood extends ArrayList<Mood> {

    private long id;
    private long userId;
    private String moodName;
    private String moodReason;
    private Date moodDate;
    private Date moodTime;

    public Mood(long id, long userId, String moodName, String moodReason, Date moodDate, Date moodTime) {
        this.id = id;
        this.userId = userId;
        this.moodName = moodName;
        this.moodReason = moodReason;
        this.moodDate = moodDate;
        this.moodTime = moodTime;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public String getMoodReason() {
        return moodReason;
    }

    public void setMoodReason(String moodReason) {
        this.moodReason = moodReason;
    }

    public Date getMoodDate() {
        return moodDate;
    }

    public void setMoodDate(Date moodDate) {
        this.moodDate = moodDate;
    }

    public Date getMoodTime() {
        return moodTime;
    }

    public void setMoodTime(Date moodTime) {
        this.moodTime = moodTime;
    }
}


