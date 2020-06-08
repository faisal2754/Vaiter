package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
        SendUserToLaunchActivity();
    }

    private void SendUserToLaunchActivity() {
        Intent launchIntent = new Intent(MainActivity.this, CustomerMainActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchIntent);
        finish();
    }
}
