package com.nooraalhassen.myapplication.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nooraalhassen on 3/30/16.
 */
public class Sleeping extends ArrayList<Sleeping> {

    private long id;
    private long userId;
    private Date sleepDate;
    private String sSleepTime;
    private String eSleepTime;
    private String sleepDur;

    public Sleeping(long id, long userId, Date sleepDate, String eSleepTime, String sSleepTime, String sleepDur) {
        this.id = id;
        this.sleepDur = sleepDur;
        this.eSleepTime = eSleepTime;
        this.sSleepTime = sSleepTime;
        this.sleepDate = sleepDate;
        this.userId = userId;
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

    public Date getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(Date sleepDate) {
        this.sleepDate = sleepDate;
    }

    public String getsSleepTime() {
        return sSleepTime;
    }

    public void setsSleepTime(String sSleepTime) {
        this.sSleepTime = sSleepTime;
    }

    public String geteSleepTime() {
        return eSleepTime;
    }

    public void seteSleepTime(String eSleepTime) {
        this.eSleepTime = eSleepTime;
    }

    public String getSleepDur() {
        return sleepDur;
    }

    public void setSleepDur(String sleepDur) {
        this.sleepDur = sleepDur;
    }
}
