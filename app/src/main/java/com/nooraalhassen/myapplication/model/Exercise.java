package com.nooraalhassen.myapplication.model;

import java.util.Date;

/**
 * Created by nooraalhassen on 3/30/16.
 */
public class Exercise {

    private long id;
    private long userId;
    private String exerType;
    private Date exerDate;
    private String sExerTime;
    private String eExerTime;
    private String exerDur;

    public Exercise(long id, long userId, String exerType, Date exerDate, String sExerTime, String eExerTime, String exerDur) {
        this.id = id;
        this.userId = userId;
        this.exerType = exerType;
        this.exerDate = exerDate;
        this.sExerTime = sExerTime;
        this.exerDur = exerDur;
        this.eExerTime = eExerTime;
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

    public String getExerType() {
        return exerType;
    }

    public void setExerType(String exerType) {
        this.exerType = exerType;
    }

    public Date getExerDate() {
        return exerDate;
    }

    public void setExerDate(Date exerDate) {
        this.exerDate = exerDate;
    }

    public String getsExerTime() {
        return sExerTime;
    }

    public void setsExerTime(String sExerTime) {
        this.sExerTime = sExerTime;
    }

    public String geteExerTime() {
        return eExerTime;
    }

    public void seteExerTime(String eExerTime) {
        this.eExerTime = eExerTime;
    }

    public String getExerDur() {
        return exerDur;
    }

    public void setExerDur(String exerDur) {
        this.exerDur = exerDur;
    }
}
