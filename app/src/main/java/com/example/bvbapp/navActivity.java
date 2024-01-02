package com.example.bvbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bvbapp.R;

public class navActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
    }

    public void ger(View view) {
        Intent i = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(i);
    }
    public void eng(View view) {
        Intent i = new Intent(getApplicationContext(),ThirdActivity.class);
        startActivity(i);
    }
}