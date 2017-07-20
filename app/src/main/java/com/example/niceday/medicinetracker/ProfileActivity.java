package com.example.niceday.medicinetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView userName;
    TextView userAge;
    TextView userEmail;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        userName = (TextView) findViewById(R.id.nameProfile);
        userAge = (TextView) findViewById(R.id.ageProfile);
        userEmail = (TextView) findViewById(R.id.emailProfile);

        String jsonUserObject="";
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            jsonUserObject = extras.getString("currentUser");
        }
        currentUser = new Gson().fromJson(jsonUserObject, User.class);
        userName.setText(currentUser.getName());
        userAge.setText(Integer.toString(currentUser.getAge()));
        userEmail.setText(currentUser.getEmail());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void createNewUser(View view){
        //Intent intent1 = new Intent(ProfileActivity.this, NewUserActivity.class);

        Intent intent1 = new Intent(this, NewUserActivity.class);

        startActivity(intent1);
    }

    public void switchUserHandler(View view) {





    }
}
