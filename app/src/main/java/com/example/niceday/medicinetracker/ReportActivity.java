package com.example.niceday.medicinetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    TextView txtReport;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String underScore = "___________________________________________";
        txtReport = (TextView) findViewById(R.id.txtReport);

        String jsonUserObject="";
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            jsonUserObject = extras.getString("currentUser");
        }
        currentUser = new Gson().fromJson(jsonUserObject, User.class);

        String finalReport = "Medicine Taking Report \r\nUser: ";
        finalReport += currentUser.getName() +"   Age: " + currentUser.getAge() +"\r\nYou have " + currentUser.getPlans().size() + " medicine(s) in record: \r\n"+underScore+"\r\n";
        Plan thisPlan = new Plan();
        for(int i=0; i< currentUser.getPlans().size();i++){
            thisPlan = currentUser.getPlans().get(i);
            String frenquence ="";
            String[] dayTime = {" Morning", " Afternoon", " Evening"};
            for(int j=0; j<3; j++) if(thisPlan.getTimesPerDay(j)) frenquence+= dayTime[j];

            finalReport += (i+1)+"):\r\nMed Name: "+thisPlan.getMedName() +"\r\nStartFrom: "+
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(thisPlan.getPlanStartTime()))+ "\r\n"+ thisPlan.getDosage()+" "+thisPlan.getUnit()+ " Every "+frenquence +
                        ". \r\nTotal Dosage: "+ thisPlan.getTotalDosage() +" "+thisPlan.getUnit() +"\r\nStill left: " + thisPlan.getLeftDosage() +" "+thisPlan.getUnit()+"\r\n"+underScore+"\r\n";
        }

        txtReport.setText(finalReport);







    }

}
