package com.nooraalhassen.myapplication.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by nooraalhassen
 */
public class Profile {
    long id;
    String name, uniname;
    Date birthdate, start_Study, grad_Study;
    char gender;

    HashMap<String, Boolean> categories;

    public void addCategory(String category, Boolean show){
        if  (!categories.keySet().contains(category)){
            categories.put(category, show);
        }

    }


    public Profile(long id, String name, Date birthdate, String uniname, Date start_Study, Date grad_Study, char gender) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.uniname = uniname;
        this.start_Study = start_Study;
        this.grad_Study = grad_Study;
        this.gender = gender;
        categories = new HashMap<>();
    }

    public long getId() { return id; }

    public String getName() { return name; }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getUniname() { return uniname; }

    public Date getStart_Study() {
        return start_Study;
    }

    public Date getGrad_Study() {
        return grad_Study;
    }

    public char getGender() {
        return gender;
    }

    public HashMap<String, Boolean> getCategories() {
        return categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setStart_Study(Date start_Study) {
        this.start_Study = start_Study;
    }

    public void setGrad_Study(Date grad_Study) {
        this.grad_Study = grad_Study;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", start_Study=" + start_Study +
                ", grad_Study=" + grad_Study +
                ", categories=" + categories +
                ", gender=" + gender +
                '}';
    }
}
