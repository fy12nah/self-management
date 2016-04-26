package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen
 */
public class ExerciseType {

    private String exerType;
    private long exerTypeID;

    public ExerciseType(String exerType, long exerTypeID) {
        this.exerType = exerType;
        this.exerTypeID = exerTypeID;
    }


    public String getExerType() {
        return exerType;
    }

    public void setExerType(String exerType) {
        this.exerType = exerType;
    }

    public long getExerTypeID() {
        return exerTypeID;
    }

    public void setExerTypeID(long exerTypeID) {
        this.exerTypeID = exerTypeID;
    }
}
