package com.nooraalhassen.myapplication.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by nooraalhassen
 */
public class Profile {
    long id;
    String name;
    Date birthdate, start_Study, grad_Study;
    char gender, profileCheck, physicalCheck, illnessCheck,  mealsCheck, moodCheck, exerCheck, sleepCheck;

    HashMap<String, Boolean> categories;

    public void addCategory(String category, Boolean show){
        if  (!categories.keySet().contains(category)){
            categories.put(category, show);
        }

    }


    public Profile(long id, String name, Date birthdate, Date start_Study, Date grad_Study, char gender) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.start_Study = start_Study;
        this.grad_Study = grad_Study;
        this.gender = gender;
        categories = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

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
