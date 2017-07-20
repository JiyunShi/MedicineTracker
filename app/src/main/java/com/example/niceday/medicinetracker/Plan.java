package com.example.niceday.medicinetracker;

/**
 * Created by NiceDay on 2017-07-19.
 */

public class Plan {

    String MedName;
    String [] unit = {"pills", "ml","mg"};
    boolean [] timesPerDay = {false,false,false};
    long planStartTime = 0;

    public long getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(long planStartTime) {
        this.planStartTime = planStartTime;
    }

    int unitindex;
    int dosage;
    int total;
    String remark;

    public String getMedName() {
        return MedName;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public boolean[] getTimesPerDay() {
        return timesPerDay;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



}
