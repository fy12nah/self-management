package com.nooraalhassen.myapplication.model;

import java.util.Date;

/**
 * Created by nooraalhassen on 3/30/16.
 */
public class Meals {

    private long id;
    private String name;
    private long userId;
    private String mealType;
    private String mealName;
    private Date mealDate;
    private Date Col_mealTime;

    public Meals(long id, String name, long userId, String mealType, Date col_mealTime, Date mealDate, String mealName) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.mealType = mealType;
        Col_mealTime = col_mealTime;
        this.mealDate = mealDate;
        this.mealName = mealName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Date getMealDate() {
        return mealDate;
    }

    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }

    public Date getCol_mealTime() {
        return Col_mealTime;
    }

    public void setCol_mealTime(Date col_mealTime) {
        Col_mealTime = col_mealTime;
    }
}
