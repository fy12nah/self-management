package com.nooraalhassen.myapplication.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nooraalhassen on 3/29/16.
 */
public class Illness extends ArrayList<Illness> {

    private long id;
    private long userID;
    private String illnessType;
    private String illnessName;
    private Date sIllnessDate;
    private Date eIllnessDate;
    private ArrayList<IllnessMed> medsList = new ArrayList<>();


    public Illness(long id, long userID, String illnessType, String illnessName, Date sIllnessDate, Date eIllnessDate) {
        this.id = id;
        this.userID = userID;
        this.illnessType = illnessType;
        this.illnessName = illnessName;
        this.sIllnessDate = sIllnessDate;
        this.eIllnessDate = eIllnessDate;
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

    public String getIllnessType() {
        return illnessType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIllnessType(String illnessType) {
        this.illnessType = illnessType;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }

    public Date getsIllnessDate() {
        return sIllnessDate;
    }

    public void setsIllnessDate(Date sIllnessDate) {
        this.sIllnessDate = sIllnessDate;
    }

    public Date geteIllnessDate() {
        return eIllnessDate;
    }

    public void seteIllnessDate(Date eIllnessDate) {
        this.eIllnessDate = eIllnessDate;
    }

    public ArrayList<IllnessMed> getMealItems() {
        return medsList;
    }

    public void add(IllnessMed item){
        medsList.add(item);
    }


}
