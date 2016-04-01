package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen on 3/31/16.
 */
public class IllnessMed {

    private long id;
    private Illness illness;
    private String illnessMed;

    public IllnessMed(Illness illness, long id, String illnessMed) {
        this.illness = illness;
        this.id = id;
        this.illnessMed = illnessMed;
    }

    public Illness getIllness() {
        return illness;
    }

    public void setIllness(Illness illness) {
        this.illness = illness;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIllnessMed() {
        return illnessMed;
    }

    public void setIllnessMed(String illnessMed) {
        this.illnessMed = illnessMed;
    }
}
