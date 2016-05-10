package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen on 5/10/16.
 */
public class Category {

    long id;
    String catName;

    public Category(long id, String catName) {
        this.id = id;
        this.catName = catName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
