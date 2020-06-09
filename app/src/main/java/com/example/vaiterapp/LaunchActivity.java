package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity {
    public Button btnStaff;
    public Button btnCustomer;
    public TextView txtAlreadyMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        btnStaff = (Button) findViewById(R.id.btnStaff);
        btnCustomer = (Button) findViewById(R.id.btnCustomer);
        txtAlreadyMember = findViewById(R.id.textViewAM);

        btnCustomer.setOnClickListener(v -> CustomerClick());
        btnStaff.setOnClickListener(v -> StaffClick());
        txtAlreadyMember.setOnClickListener(c -> LoginClick());

    }


    public void StaffClick(){
        Intent staffIntent = new Intent(LaunchActivity.this, StaffSignupActivity.class);
        startActivity(staffIntent);

    }

    public void LoginClick(){
        Intent staffIntent = new Intent(LaunchActivity.this, LoginActivity.class);
        startActivity(staffIntent);

    }

    public void CustomerClick(){
        Intent customerIntent = new Intent(LaunchActivity.this, SignupActivity.class);
        startActivity(customerIntent);

    }



}
