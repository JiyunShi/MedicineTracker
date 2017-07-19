package com.example.niceday.medicinetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class NewUserActivity extends AppCompatActivity {

    public static final String FILE_NAME = "usr_db.txt";
    EditText newUserName;
    EditText newPassword;
    EditText newAge;
    EditText newEmail;
    RadioButton genderMale;
    RadioButton genderFemale;
    List<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New User");

        newUserName = (EditText) findViewById(R.id.newUserName);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newAge = (EditText) findViewById(R.id.newAge);
        newEmail = (EditText)findViewById(R.id.newEmail);
        genderMale = (RadioButton) findViewById(R.id.genderMale);
        genderFemale = (RadioButton) findViewById(R.id.genderFemale);
    }

    public void signUpHandler(View view) {

        User newUser = new User();
        newUser.setName(newUserName.getText().toString());
        newUser.setPassword(newPassword.getText().toString());
        newUser.setAge(Integer.parseInt(newAge.getText().toString()));
        newUser.setEmail(newEmail.getText().toString());
        if(genderMale.isChecked()) newUser.setMale(true);
        else if(genderFemale.isChecked()) newUser.setMale(false);
        userList.add(newUser);

        boolean result = JSONHelper.exportToJSON(this, userList);

        if(result) Toast.makeText(this, "create New User Successfully!!", Toast.LENGTH_LONG).show();
        else Toast.makeText(this,"create New User failed", Toast.LENGTH_LONG).show();


    }
}
