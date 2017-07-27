package com.example.niceday.medicinetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class ShowDetailActivity extends AppCompatActivity {

    TextView txtDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Today's Detail");


        txtDetail = (TextView) findViewById(R.id.txtDetail);
        txtDetail.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            String content = extras.getString("displayContent");
            txtDetail.setText(content);
        }



    }

}
