package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences prf1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout lin = new LinearLayout(this);
        lin.setGravity(Gravity.CENTER);
        lin.setOrientation(LinearLayout.VERTICAL);
        setContentView(lin);


    }

    protected void onStart() {
        super.onStart();

        //SendUserToLaunchActivity();

        prf1 = getSharedPreferences("user_details",MODE_PRIVATE);
        boolean staff = prf1.getBoolean("staff", false);
        String email = prf1.getString("email", "");

        if(email.equalsIgnoreCase("")){

            SendUserToLaunchActivity();
        }
        else if(staff == false){
            //Toast.makeText(this, prf1.getInt("userID", 27))
            SendUserToMainActivity();
        }
        else if(staff = true){
            SendUserToStaffActivity();
        }

    }



    private void SendUserToLaunchActivity() {
        Intent launchIntent = new Intent(MainActivity.this, LaunchActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchIntent);
        finish();
    }

    private void SendUserToMainActivity() {
        Intent launchIntent = new Intent(MainActivity.this, CustomerMainActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchIntent);
        finish();
    }

    private void SendUserToStaffActivity() {
        Intent launchIntent = new Intent(MainActivity.this, StaffMainActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchIntent);
        finish();
    }
}
