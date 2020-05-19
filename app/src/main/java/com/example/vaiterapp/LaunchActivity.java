package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {
    public Button btnStaff;
    public Button btnCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        btnStaff = (Button) findViewById(R.id.btnStaff);
        btnCustomer = (Button) findViewById(R.id.btnCustomer);

        btnCustomer.setOnClickListener(v -> CustomerClick());
        btnStaff.setOnClickListener(v -> StaffClick());

    }


    public void StaffClick(){
        Intent staffIntent = new Intent(LaunchActivity.this, StaffSignupActivity.class);
        staffIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(staffIntent);
        finish();
    }

    public void CustomerClick(){
        Intent customerIntent = new Intent(LaunchActivity.this, SignupActivity.class);
        customerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(customerIntent);
        finish();
    }

}
