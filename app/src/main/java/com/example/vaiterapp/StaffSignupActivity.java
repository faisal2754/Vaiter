package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class StaffSignupActivity extends AppCompatActivity {
    public TextView txtAlreadyM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_signup);
        txtAlreadyM = findViewById(R.id.textAlreadyMember);
        Spinner staticSpinner = (Spinner) findViewById(R.id.spinnerRestaurant);




        txtAlreadyM.setOnClickListener(v -> LoginClick());
    }

    public void LoginClick(){
        Intent staffIntent = new Intent(StaffSignupActivity.this, LoginActivity.class);
        staffIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(staffIntent);
        finish();
    }
}
