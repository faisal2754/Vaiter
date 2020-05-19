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
        TextView t = new TextView(this);
        t.setText("Nigga wtf");
        lin.addView(t);
        Button b = new Button(this);
        b.setText("go to login");
        lin.addView(b);
        b.setOnClickListener(v -> SendUserToLoginActivity());
    }

    /*protected void onStart() {
        super.onStart();
        SendUserToLoginActivity();
    }*/

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}
