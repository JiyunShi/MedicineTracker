package com.example.niceday.medicinetracker;

import java.util.ArrayList;
import java.util.List;

//class for User info

public class User {

    String name = "guest";
    String password = "0000000";
    String email = "guest@testing.com";
    boolean isMale = true;

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    List<Plan> plans = new ArrayList<Plan>();
    int age = 0;

    public User(){}
    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
