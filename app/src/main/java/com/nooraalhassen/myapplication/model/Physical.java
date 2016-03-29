package com.nooraalhassen.myapplication.model;

import java.util.Date;

/**
 * Created by nooraalhassen on 3/29/16.
 */
public class Physical {

    private long id;
    private long userID;
    private float weight = -1;
    private float height = -1;
    private Date date;

    public Physical(long id, long userID, float weight, float height, Date date) {
        this.id = id;
        this.weight = weight;
        this.userID = userID;
        this.height = height;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
