package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen on 3/31/16.
 */
public class MealItem {

    private long id;
    private Meal meal;
    private String mealItem;

    public MealItem(long id, Meal meal, String mealItem) {
        this.id = id;
        this.meal = meal;
        this.mealItem = mealItem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getMealItem() {
        return mealItem;
    }

    public void setMealItem(String mealItem) {
        this.mealItem = mealItem;
    }
}
