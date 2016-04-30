package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen
 */
public class IllnessName {

    private String illnessName;
    private long illnessNameID;

    public IllnessName(String illnessName, long illnessNameID) {
        this.illnessName = illnessName;
        this.illnessNameID = illnessNameID;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }

    public long getIllnessNameID() {
        return illnessNameID;
    }

    public void setIllnessNameID(long illnessNameID) {
        this.illnessNameID = illnessNameID;
    }
}
