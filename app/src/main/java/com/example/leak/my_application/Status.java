package com.example.leak.my_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class Status extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void jumpToLoginPersonnel(View view) {
        Intent intent = new Intent(this, LoginPersonnel.class);
        startActivity(intent);
    }

    public void jumpToLoginVisiteur(View view) {
        Intent intent = new Intent(this, LoginVisiteur.class);
        startActivity(intent);
    }
}
