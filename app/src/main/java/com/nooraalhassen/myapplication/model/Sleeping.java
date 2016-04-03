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
    private Date sSleepTime;
    private Date eSleepTime;
    private int sleepDur;

    public Sleeping(long id, long userId, Date sleepDate, Date eSleepTime, Date sSleepTime, int sleepDur) {
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

    public Date getsSleepTime() {
        return sSleepTime;
    }

    public void setsSleepTime(Date sSleepTime) {
        this.sSleepTime = sSleepTime;
    }

    public Date geteSleepTime() {
        return eSleepTime;
    }

    public void seteSleepTime(Date eSleepTime) {
        this.eSleepTime = eSleepTime;
    }

    public float getSleepDur() {
        return sleepDur;
    }

    public void setSleepDur(int sleepDur) {
        this.sleepDur = sleepDur;
    }
}
