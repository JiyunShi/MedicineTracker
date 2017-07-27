package com.example.niceday.medicinetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class SwitchUserActivity extends AppCompatActivity {

    User currentUser;
    TextView txtCurrentUser;
    Spinner spinUsername;
    EditText txtpassword;
    String[] arraySpinner;
    List<User> users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Switch User");

        String jsonUserObject="";
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            jsonUserObject = extras.getString("currentUser");
        }
        currentUser = new Gson().fromJson(jsonUserObject, User.class);

        txtCurrentUser = (TextView) findViewById(R.id.txtCurrentUser);
        spinUsername = (Spinner) findViewById(R.id.spinUserName);
        txtpassword = (EditText) findViewById(R.id.txtPwinput);

        txtCurrentUser.setText(currentUser.getName());

        users = JSONHelper.getDB(this).getUsers();
        List<String> userNames = new ArrayList<String>();
        for(int i= 0; i<users.size();i++){
            userNames.add(users.get(i).getName());
        }
        arraySpinner = userNames.toArray(new String[userNames.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinUsername.setAdapter(adapter);





    }

    public void switchUserConfirm(View view) {

        String password = txtpassword.getText().toString().trim();
        if(password.isEmpty()){
            Toast.makeText(SwitchUserActivity.this, "Please input your password", Toast.LENGTH_LONG).show();
            return;
        }

        String selectedUser = spinUsername.getSelectedItem().toString();

        for(int i=0;i<users.size();i++){
            if(selectedUser.equals(users.get(i).getName())){
                if(password.equals(users.get(i).getPassword())){
                    JSONHelper.updateDB(this, users, selectedUser);
                    Toast.makeText(SwitchUserActivity.this, "log in account " +selectedUser+" successfully! Thank you!", Toast.LENGTH_LONG).show();
                    Intent backtoHome = new Intent(this, MainActivity.class);
                    backtoHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backtoHome);
                    this.finish();;

                }else{
                    Toast.makeText(SwitchUserActivity.this, "The password you input is incorrect, please try again!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }





    }
}
