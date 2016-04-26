package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen on 4/26/16.
 */
public class MoodName {

    private String moodName;
    private long moodNameID;

    public MoodName(String moodName, long moodNameID) {
        this.moodName = moodName;
        this.moodNameID = moodNameID;
    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public long getMoodNameID() {
        return moodNameID;
    }

    public void setMoodNameID(long moodNameID) {
        this.moodNameID = moodNameID;
    }
}
