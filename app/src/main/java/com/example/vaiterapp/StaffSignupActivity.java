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

import java.util.ArrayList;
import java.util.List;

public class StaffSignupActivity extends AppCompatActivity {
    public TextView txtAlreadyM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_signup);
        txtAlreadyM = findViewById(R.id.textAlreadyMember);
        Spinner staticSpinner = (Spinner) findViewById(R.id.spinnerRestaurant);
        txtAlreadyM.setOnClickListener(v -> LoginClick());

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Select your restaurant");
        spinnerArray.add("McDonalds");
        spinnerArray.add("Ocean Basket");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinnerRestaurant);
        sItems.setAdapter(adapter);

//        String selected = sItems.getSelectedItem().toString();
//            if (selected.equals("what ever the option was")) {
//        }

    }

    public void LoginClick(){
        Intent staffIntent = new Intent(StaffSignupActivity.this, LoginActivity.class);
        startActivity(staffIntent);
    }
}
