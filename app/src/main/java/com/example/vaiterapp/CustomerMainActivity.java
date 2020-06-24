package com.example.vaiterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vaiterapp.ui.main.SectionsPagerAdapter;

public class CustomerMainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    SharedPreferences prf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Vaiter");
        getSupportActionBar().setLogo(R.drawable.logo_small);


        tabs.getTabAt(0).setIcon(R.drawable.home);
        tabs.getTabAt(1).setIcon(R.drawable.orders);
        tabs.getTabAt(2).setIcon(R.drawable.history);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.user_menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.logout_option){
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            logout();
            SendUserToLaunchActivity();
        }
        return true;
//        switch (item.getItemId()){
//            case R.id.logout_option:
//                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
//                logout();
//                SendUserToLaunchActivity();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

    }

    private void SendUserToLaunchActivity() {
        Intent launchIntent = new Intent(CustomerMainActivity.this, LaunchActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchIntent);
        finish();
    }

    public void logout(){
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.commit();
        SendUserToLaunchActivity();
    }
}