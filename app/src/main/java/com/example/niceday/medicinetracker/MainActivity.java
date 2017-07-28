package com.example.niceday.medicinetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    User currentUser;
    NavigationView navigationView;
    List<Plan> currentPlans = new ArrayList<Plan>();
    TextView nextDoze, todayTotal;
    int timeindex;
    boolean flagNext =false, flagTotal = false, flagNoTaken=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set fab onclick handler, open new activity Add new Medicine.
                Intent intentNewMedicine = new Intent(MainActivity.this, AddMedicineActivity.class);
                intentNewMedicine.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentNewMedicine);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //update content onResume.
        currentUser = JSONHelper.getCurrentUser(this);
        //if open app first time, create default guest user and the json file.
        if(currentUser==null){
            currentUser = new User();
            currentUser.setName("Guest User");
            currentUser.setEmail("Please sign up asap!");
            currentUser.setAge(0);
            List<User> guestUsers = new ArrayList<User>();
            guestUsers.add(currentUser);
            JSONHelper.updateDB(this, guestUsers,currentUser.getName());
        }
        //set up Navigation panel content
        this.setUpNavText();
        //set up medicine display contetn on main page
        this.displayNext();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            //start profile activity
            case R.id.nav_profile:
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                intentProfile.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentProfile);
                break;
            //start new Medicine activity
            case R.id.nav_add_newMedicine:
                Intent intentNewMedicine = new Intent(this, AddMedicineActivity.class);
                intentNewMedicine.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentNewMedicine);
                break;
            //start report activity
            case R.id.nav_report:
                Intent intentReport = new Intent(this, ReportActivity.class);
                intentReport.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentReport);
                break;
            //start about us activity
            case R.id.nav_send:
                Intent intentAbout = new Intent(this,AboutUsActivity.class);
                startActivity(intentAbout);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUpNavText(){

        View hView = navigationView.getHeaderView(0);
        nextDoze = (TextView) findViewById(R.id.txtNextDoze);
        todayTotal = (TextView) findViewById(R.id.txtTodayTotal);

        TextView mainUserName = hView.findViewById(R.id.userNameMain);
        TextView emailMain = hView.findViewById(R.id.emailMain);
        mainUserName.setText(currentUser.getName());
        emailMain.setText(currentUser.getEmail());

    }




    //Handle nextDoze TextView content display
    public void displayNext(){

        currentPlans = currentUser.getPlans();
        //hanel caes no medicine info for current user
        if(currentPlans.size()==0){
            nextDoze.setText("You don't have any medicine to take right now");
            todayTotal.setText("You don't have any medicine to take today");
        }else{

            Plan thisPlan = new Plan();
            String displayNextDoze ="";
            String displayTotal = "";
            String displayNextDozeTemp="";

            //set Calendar to today 0:00:00.000
            Calendar cal = Calendar.getInstance();
            //current date in format yyyy-MM-dd.
            displayNextDoze += new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+ " ";
            displayTotal += displayNextDoze + "\r\n";
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            //set flag to indentify morning, afternoon and evening time.
            long morningStartMilli = cal.getTimeInMillis();
            //add 8 hours, so it's 8:00:00.000
            cal.add(Calendar.HOUR_OF_DAY, 8);
            long morningOverAfternoonStartMilli = cal.getTimeInMillis();
            //add 8 hours, it's 16:00:00.000
            cal.add(Calendar.HOUR_OF_DAY,8);
            long AfternoonOverNightStartMilli = cal.getTimeInMillis();
            //add 8 hours, it's tomorrow 0:00:00.000
            cal.add(Calendar.HOUR_OF_DAY,8);
            long currentTime = currentTimeMillis();
            //compare with current time to define current time range
            if(currentTime>=morningStartMilli&&currentTime<morningOverAfternoonStartMilli){
                timeindex=0;
                displayNextDoze +="Morning: \r\n";
            }else if(currentTime>=morningOverAfternoonStartMilli&&currentTime<AfternoonOverNightStartMilli){
                timeindex=1;
                displayNextDoze +="Afternoon: \r\n";
            }else{
                timeindex = 2;
                displayNextDoze +="Evening: \r\n";
            }
            displayNextDozeTemp = displayNextDoze;

            for(int i=0; i<currentPlans.size();i++) {

                thisPlan = currentPlans.get(i);
                String newDay = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                //check if it's a new day compare with the record
                if(!thisPlan.getCurrentDay().equals(newDay)){
                    thisPlan.setCurrentDay(newDay);
                    //if yes, set all medicine isTaken to false;
                    for(int j=0; j<3; j++) thisPlan.setHasTaken(false, j);
                }

                //add up total amount for today for this medicine
                int totalAmount =0;
                for(int j=0;j<3;j++){
                    if(thisPlan.getTimesPerDay(j)) totalAmount+=thisPlan.getDosage();
                }
                //display this medicine if it's still need to take(not finished)
                if(thisPlan.getLeftDosage()>0) {
                    displayTotal += thisPlan.getMedName()+": " +totalAmount +" " + thisPlan.getUnit()+"\r\n";
                    flagTotal=true;
                }

                //display this medicine in nextDoze, if it need to be taken in current timeline.
                if(thisPlan.getLeftDosage()>0&&thisPlan.getTimesPerDay(timeindex)){
                    displayNextDoze += thisPlan.getMedName()+": "+thisPlan.getDosage()+" "+thisPlan.getUnit()+"\r\n";
                    flagNext=true;
                    flagNoTaken=true;
                    if(thisPlan.getHasTaken(timeindex)) {
                        displayNextDoze +="--done \r\n";
                        flagNoTaken=false;
                    }
                }

            }
            if(flagNext&&flagNoTaken) nextDoze.setText(displayNextDoze);
            //if nothing to taken or all already taken
            else if(flagNext&&!flagNoTaken){
                displayNextDoze = displayNextDozeTemp;
                displayNextDoze +="You have taken all medicines already at this time!";
                nextDoze.setText(displayNextDoze);
            }
            else{
                displayNextDoze +="You don't have any medicine to take at this time!";
                nextDoze.setText(displayNextDoze);
            }
            if(flagTotal) todayTotal.setText(displayTotal);
            else{
                displayTotal +="You don't have any medicine to take today!";
                todayTotal.setText(displayTotal);
            }
        }

    }



    //handle take action button on main page
    public void takeActionHandler(View view) {
        if(currentPlans.size()==0){
            Toast.makeText(this, "You don't have any medicine to take, Please add some first!!", Toast.LENGTH_LONG).show();
        }
        else if(flagNext&&!flagNoTaken){
            Toast.makeText(this, "You have already taken all the medcine at this time, come back later for next time!!", Toast.LENGTH_LONG).show();
        }else {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Has taken the medicine?");
            alertDialog.setMessage("Are you sure you have taken these medicine already?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Plan MedicineToTake = new Plan();
                    List<Plan> newPlans = new ArrayList<Plan>();
                    for (int i = 0; i < currentPlans.size(); i++) {
                        MedicineToTake = currentPlans.get(i);
                        if (MedicineToTake.getLeftDosage() > 0 && MedicineToTake.getTimesPerDay(timeindex) && !MedicineToTake.getHasTaken(timeindex)) {
                            MedicineToTake.setLeftDosage(MedicineToTake.getLeftDosage() - MedicineToTake.getDosage());
                            MedicineToTake.setHasTaken(true, timeindex);
                        }

                        newPlans.add(MedicineToTake);
                        currentUser.setPlans(newPlans);
                        //update the userList, remove the old one and add the updated one
                        List<User> updatedUserList = JSONHelper.updateDBprefix(MainActivity.this, currentUser);
                        //re write the new userList to the Json file.
                        JSONHelper.updateDB(MainActivity.this, updatedUserList, currentUser.getName());
                    }
                    //refresh the current main page
                    finish();
                    startActivity(getIntent());
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });

            alertDialog.show();
        }
    }

    //handle show Detail button on main page
    public void showDetialHandler(View view) {
        if(currentPlans.size()==0){
            Toast.makeText(this, "You don't have any medicine to take, Please add some first!!", Toast.LENGTH_LONG).show();
            return;
        }
        Plan thisPlan;
        String displayTotal="";
        for(int i=0; i<currentPlans.size();i++) {

            thisPlan = currentPlans.get(i);

            String frenquence ="";
            String[] dayTime = {" Morning", " Afternoon", " Evening"};
            String underScore = "\r\n_____________________________________________\r\n \r\n";
            int totalAmount =0;
            for(int j=0;j<3;j++){
                if(thisPlan.getTimesPerDay(j)) {
                    totalAmount+=thisPlan.getDosage();
                    frenquence+= dayTime[j];
                }
            }
            if(thisPlan.getLeftDosage()>0) {
                displayTotal += "Medicine Name:     " +thisPlan.getMedName()+"\r\nToday's Total:    " +totalAmount +" " + thisPlan.getUnit()+"\r\nDosage:    " +thisPlan.getDosage()
                        +" " + thisPlan.getUnit()+"\r\nTake When:    "+frenquence +"\r\nTotal amounts:   "
                        +thisPlan.getTotalDosage()+" " + thisPlan.getUnit()+"\r\nRemark:     "+thisPlan.getRemark()+underScore;
                flagTotal=true;
            }
            //if there's medicine to take today, start the Detial Activity.
            if(flagTotal){
                Intent intentDetail = new Intent(this, ShowDetailActivity.class);
                intentDetail.putExtra("displayContent", displayTotal);
                startActivity(intentDetail);
            }

        }




    }
}
