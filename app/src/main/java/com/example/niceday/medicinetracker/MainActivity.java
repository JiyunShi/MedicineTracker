package com.example.niceday.medicinetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        currentUser = JSONHelper.getCurrentUser(this);
        if(currentUser==null){
            currentUser = new User();
            currentUser.setName("Guest User");
            currentUser.setEmail("Please sign up asap!");
            currentUser.setAge(0);
        }

        this.setUpNavText();
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
            case R.id.nav_profile:
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                intentProfile.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentProfile);

                break;
            case R.id.nav_add_newMedicine:
                Intent intentNewMedicine = new Intent(this, AddMedicineActivity.class);
                intentNewMedicine.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentNewMedicine);
                break;
            case R.id.nav_report:
                Intent intentReport = new Intent(this, ReportActivity.class);
                intentReport.putExtra("currentUser", new Gson().toJson(currentUser));
                startActivity(intentReport);
                break;
            case R.id.nav_send:

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
        if(currentPlans.size()==0){
            nextDoze.setText("You don't have any medicine to take right now");
            todayTotal.setText("You don't have any medicine to take today");
        }else{

            Plan thisPlan = new Plan();
            String displayNextDoze ="";
            String displayTotal = "";

            //set Calendar to today 0:00:00.000
            Calendar cal = Calendar.getInstance();
            displayNextDoze += new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+ " ";
            displayTotal += displayNextDoze + "\r\n";
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long morningStartMilli = cal.getTimeInMillis();
            //add 8 hours, so it's 8:00:00.000
            cal.add(Calendar.HOUR_OF_DAY, 8);
            long morningOverAfternoonStartMilli = cal.getTimeInMillis();
            //add 8 hours, it's 16:00:00.000
            cal.add(Calendar.HOUR_OF_DAY,8);
            long AfternoonOverNightStartMilli = cal.getTimeInMillis();
            //add 8 hours, it's tomorrow 0:00:00.000
            cal.add(Calendar.HOUR_OF_DAY,8);
            long NightOverMilli = cal.getTimeInMillis();
            long currentTime = currentTimeMillis();
            if(currentTime>=morningStartMilli&&currentTime<morningOverAfternoonStartMilli){
                timeindex=0;
                displayNextDoze +="Morning: \r\n";
            }else if(currentTime>=morningOverAfternoonStartMilli&&currentTime<AfternoonOverNightStartMilli){
                timeindex=1;
                displayNextDoze +="Afternoong: \r\n";
            }else{
                timeindex = 2;
                displayNextDoze +="Evening: \r\n";
            }
            boolean flagNext =false, flagTotal = false;
            for(int i=0; i<currentPlans.size();i++) {

                thisPlan = currentPlans.get(i);
                String newDay = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                if(!thisPlan.getCurrentDay().equals(newDay)){
                    thisPlan.setCurrentDay(newDay);
                    for(int j=0; j<3; j++) thisPlan.setHasTaken(false, j);
                }

                int totalAmount =0;
                for(int j=0;j<3;j++){
                    if(thisPlan.getTimesPerDay(j)) totalAmount+=thisPlan.getDosage();
                }
                if(thisPlan.getLeftDosage()>0) {
                    displayTotal += thisPlan.getMedName()+": " +totalAmount + thisPlan.getUnit()+"\r\n";
                    flagTotal=true;
                }


                if(thisPlan.getLeftDosage()>0&&thisPlan.getTimesPerDay(timeindex)){
                    displayNextDoze += thisPlan.getMedName()+": "+thisPlan.getDosage()+ thisPlan.getUnit()+"\r\n";
                    flagNext=true;
                    if(thisPlan.getHasTaken(timeindex)) displayNextDoze +="--done \r\n";
                }

            }
            if(flagNext) nextDoze.setText(displayNextDoze);
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




    public void takeActionHandler(View view) {
        Plan MedicineToTake = new Plan();
        List<Plan> newPlans = new ArrayList<Plan>();
        for(int i= 0; i<currentPlans.size();i++){
            MedicineToTake = currentPlans.get(i);
            if(MedicineToTake.getLeftDosage()>0&&MedicineToTake.getTimesPerDay(timeindex)&&!MedicineToTake.getHasTaken(timeindex)){
                MedicineToTake.setLeftDosage(MedicineToTake.getLeftDosage()-MedicineToTake.getDosage());
                MedicineToTake.setHasTaken(true, timeindex);
            }

            newPlans.add(MedicineToTake);
            currentUser.setPlans(newPlans);
            List<User> updatedUserList = JSONHelper.updateDBprefix(this,currentUser);
            JSONHelper.updateDB(this, updatedUserList, currentUser.getName());
        }


        finish();
        startActivity(getIntent());

    }
}
