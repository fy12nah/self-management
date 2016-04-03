package com.nooraalhassen.myapplication.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nooraalhassen on 3/30/16.
 */
public class Meal {

    private long id;
    private long userId;
    private String mealType;
    private String mealName;
    private Date mealDate;
    private Date mealTime;
    private ArrayList<MealItem> mealItems = new ArrayList<>();

    public Meal(long id, long userId, String mealType, String mealName, Date mealDate, Date mealTime) {
        this.id = id;
        this.userId = userId;
        this.mealType = mealType;
        this.mealTime = mealTime;
        this.mealDate = mealDate;
        this.mealName = mealName;
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


    public Date getMealTime() {
        return mealTime;
    }

    public void setMealTime(Date mealTime) {
        this.mealTime = mealTime;
    }

    public ArrayList<MealItem> getMealItems() {
        return mealItems;
    }

    public void add(MealItem item){
        mealItems.add(item);
    }

}
