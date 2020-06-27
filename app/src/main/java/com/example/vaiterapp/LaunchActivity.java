package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

public class LaunchActivity extends AppCompatActivity {
    public Button btnStaff;
    public Button btnCustomer;
    public TextView txtAlreadyMember;

    private ImageView iTwitter;
    private ImageView iInstagram;
    private ImageView iFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        btnStaff = (Button) findViewById(R.id.btnStaff);
        btnCustomer = (Button) findViewById(R.id.btnCustomer);
        txtAlreadyMember = findViewById(R.id.textViewAM);

        iTwitter = (ImageView) findViewById(R.id.twitter);
        iInstagram = (ImageView) findViewById(R.id.instagram);
        iFacebook = (ImageView) findViewById(R.id.facebook);

        iTwitter.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/AppVaiter"));
                startActivity(intent);
            }
        });

        iInstagram.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/vaiterapp/"));
                startActivity(intent);
            }
        });

        iFacebook.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/vaiter.app.3"));
                startActivity(intent);
            }
        });


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
        Intent customerIntent = new Intent(LaunchActivity.this, CustomerSignupActivity.class);
        startActivity(customerIntent);

    }



}
