package com.example.niceday.medicinetracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//class for medicine plans
public class Plan {

    String MedName;
    String [] unit = {"pills", "ml","mg"};
    boolean [] timesPerDay = {false,false,false};
    long planStartTime = 0;
    String currentDay;
    boolean [] hasTaken = {false,false,false};
    int unitindex;
    int dosage;
    int totalDay;
    String remark;
    int totalDosage;
    int leftDosage;

    public Plan(){}

    public Plan(long seconds,  int dosage, int totalDay, int unitindex, String newName,String remark, boolean[] timesperday){

        this.planStartTime = seconds;
        this.dosage = dosage;
        this.totalDay=totalDay;
        this.unitindex=unitindex;
        this.MedName=newName;
        this.remark=remark;
        this.timesPerDay = timesperday;
        int times = 0;
        for(int i=0; i<3;i++){
            if(timesPerDay[i]==true) times++;
        }

        this.totalDosage = dosage*times*totalDay;
        this.leftDosage=this.totalDosage;
        this.setCurrentDay(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));


    }


    public String getUnit(){
        return this.unit[this.unitindex];
    }

    public int getTotalDosage() {
        return totalDosage;
    }

    public void setTotalDosage() {

        int times = 0;
        for(int i=0; i<3;i++){
            if(timesPerDay[i]==true) times++;
        }

        this.totalDosage = dosage*times*totalDay;
    }

    public int getLeftDosage() {
        return leftDosage;
    }

    public void setLeftDosage(int leftDosage) {
        this.leftDosage = leftDosage;
    }

    public boolean getHasTaken(int i){
        return hasTaken[i];
    }

    public void setHasTaken(boolean flag, int i){
        this.hasTaken[i]=flag;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String day) {

        this.currentDay = day;
    }



    public long getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(long planStartTime) {
        this.planStartTime = planStartTime;
    }



    public String getMedName() {
        return MedName;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public boolean getTimesPerDay(int i) {
        return timesPerDay[i];
    }

    public void setTimesPerDay(boolean[] timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public int getUnitindex() {
        return unitindex;
    }

    public void setUnitindex(int unitindex) {
        this.unitindex = unitindex;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



}
