package com.example.vaiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView txtLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLaunch = findViewById(R.id.textViewNoAcc);

        txtLaunch.setOnClickListener(v -> SignupClick());
    }

    public void SignupClick(){
        Intent staffIntent = new Intent(LoginActivity.this, LaunchActivity.class);
        staffIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(staffIntent);
        finish();
    }
}