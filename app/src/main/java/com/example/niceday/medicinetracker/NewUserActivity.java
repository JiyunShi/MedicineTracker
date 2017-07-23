package com.example.niceday.medicinetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
        newUser.setName(newUserName.getText().toString().trim());
        //validate name input
        if(this.nameValid(newUser.getName())) return;
        newUser.setPassword(newPassword.getText().toString().trim());
        //validate pw input
        if(newUser.getPassword().isEmpty()){
            Toast.makeText(NewUserActivity.this, "Please input your password", Toast.LENGTH_LONG).show();
            return;
        }
        try{
        newUser.setAge(Integer.parseInt(newAge.getText().toString()));}
        catch (Exception e){
            Toast.makeText(NewUserActivity.this, "Please input your age correctly", Toast.LENGTH_LONG).show();
            return;
        }
        newUser.setEmail(newEmail.getText().toString());
        if(newUser.getEmail().isEmpty()) {
            Toast.makeText(NewUserActivity.this, "Please input your email correctly", Toast.LENGTH_LONG).show();
            return;
        }
        if(genderMale.isChecked()||genderFemale.isChecked()) {
            if (genderMale.isChecked()) newUser.setMale(true);
            else if (genderFemale.isChecked()) newUser.setMale(false);
        }else{
            Toast.makeText(NewUserActivity.this, "Please select your gender correctly", Toast.LENGTH_LONG).show();
            return;
        }


        if(JSONHelper.getDB(this)==null) userList = new ArrayList<User>();
        else userList = JSONHelper.getDB(this).getUsers();

        userList.add(newUser);

        boolean result = JSONHelper.updateDB(this, userList, newUser.getName());

        if(result) Toast.makeText(this, "create New User Successfully!!", Toast.LENGTH_LONG).show();
        else Toast.makeText(this,"create New User failed", Toast.LENGTH_LONG).show();

        Intent backtoHome = new Intent(this, MainActivity.class);
        backtoHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(backtoHome);
        this.finish();;


    }

    public boolean nameValid(String name){
        if(name.isEmpty()){
            Toast.makeText(NewUserActivity.this, "Please input your username", Toast.LENGTH_LONG).show();
            return true;
        }
        if(JSONHelper.getDB(NewUserActivity.this)==null) return false;

        List<User> userlist = JSONHelper.getDB(NewUserActivity.this).getUsers();
        for(int i=0; i<userlist.size();i++){
            if(name.equals(userlist.get(i).getName())){
                Toast.makeText(NewUserActivity.this, "User name is already exist, please try another name", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        return false;
    }



}
