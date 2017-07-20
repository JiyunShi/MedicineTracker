package com.example.niceday.medicinetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.System.currentTimeMillis;

public class AddMedicineActivity extends AppCompatActivity {

    User currentUser;
    List<Plan> plans = new ArrayList<Plan>();
    TextView newMedicineName, dosage, totaldays, medicineRemark;
    Spinner medicineUnits;
    CheckBox ckboxMorning, ckboxAfternoon, ckboxEvening;
    List<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newMedicineName = (TextView) findViewById(R.id.newMedicineName);
        dosage = (TextView) findViewById(R.id.dosage);
        totaldays = (TextView) findViewById(R.id.totaldays);
        medicineRemark = (TextView) findViewById(R.id.medicineRemark);
        medicineUnits = (Spinner) findViewById(R.id.medicineUnits);
        ckboxMorning = (CheckBox) findViewById(R.id.ckboxMorning);
        ckboxAfternoon = (CheckBox) findViewById(R.id.ckboxAfternoon);
        ckboxEvening = (CheckBox) findViewById(R.id.ckboxEvening);
    }

    public void addNewMedicineHandler(View view) {

        String jsonUserObject="";
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            jsonUserObject = extras.getString("currentUser");
        }
        currentUser = new Gson().fromJson(jsonUserObject, User.class);

        plans = currentUser.getPlans();

        //get input
        Plan newplan = new Plan();
        String newName = newMedicineName.getText().toString();
        int newDosage = Integer.parseInt(dosage.getText().toString());
        int total = Integer.parseInt(totaldays.getText().toString());
        String remark = medicineRemark.getText().toString();
        int unit = medicineUnits.getSelectedItemPosition();
        boolean[] times={false,false,false};
        if(ckboxMorning.isChecked()) times[0]=true;
        if(ckboxAfternoon.isChecked()) times[1]=true;
        if(ckboxEvening.isChecked()) times[2]=true;
        //plan start time
        long seconds = currentTimeMillis();


        newplan.setPlanStartTime(seconds);
        newplan.setMedName(newName);
        newplan.setDosage(newDosage);
        newplan.setTotal(total);
        newplan.setRemark(remark);
        newplan.setUnitindex(unit);
        newplan.setTimesPerDay(times);



        plans.add(newplan);
        currentUser.setPlans(plans);
        userList.add(currentUser);
        boolean result = JSONHelper.updateDB(this, userList, currentUser.getName());

        if(result) Toast.makeText(this, "create new Medicine Plan Successfully!!", Toast.LENGTH_LONG).show();
        else Toast.makeText(this,"create new plan failed", Toast.LENGTH_LONG).show();
        this.finish();

    }
}