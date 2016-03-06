package com.nooraalhassen.myapplication.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nooraalhassen on 3/6/16.
 */
public class Profile {

    String name;
    Date birthdate, start_Study, grad_Study;
    char gender, profileCheck, physicalCheck, illnessCheck,  mealsCheck, moodCheck, exerCheck, sleepCheck;

    ArrayList <String> categories;

    public Profile(String name, Date birthdate, Date start_Study, Date grad_Study, char gender) {
        this.name = name;
        this.birthdate = birthdate;
        this.start_Study = start_Study;
        this.grad_Study = grad_Study;
        this.gender = gender;
    }


}
