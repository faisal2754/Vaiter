package com.example.vaiterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.vaiterapp.ui.main.SectionPagerAdapterStaff;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vaiterapp.ui.main.SectionsPagerAdapter;

public class StaffMainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);
        SectionPagerAdapterStaff sectionsPagerAdapter = new SectionPagerAdapterStaff(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Vaiter for Staff");
        getSupportActionBar().setLogo(R.drawable.logo_small);

        tabs.getTabAt(0).setIcon(R.drawable.reservations_colour);
        tabs.getTabAt(1).setIcon(R.drawable.history_colour);






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


//        optionsMenu.getItem(1).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

//        MenuItem darkmode = optionsMenu.findItem(R.id.darkmode_option);
//        MenuItem lightmode = optionsMenu.findItem(R.id.lightmode_option);


        if(item.getItemId() == R.id.logout_option){
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            logout();
            SendUserToLaunchActivity();
        }
        else if(item.getItemId() == R.id.darkmode_option){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.lightmode_option){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(this, "Light Mode", Toast.LENGTH_SHORT).show();
//

        }
        else if(item.getItemId() == R.id.about_option){
            openDialog();
        }
        else if(item.getItemId() == R.id.profile_option){
            ProfileClick();
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

    private void openDialog() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(getSupportFragmentManager(), "about dialog");
    }

    public void ProfileClick(){
        Intent staffIntent = new Intent(StaffMainActivity.this, ProfileActivity.class);
        startActivity(staffIntent);
    }

    private void SendUserToLaunchActivity() {
        Intent launchIntent = new Intent(StaffMainActivity.this, LaunchActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchIntent);
        finish();
    }

    public void logout(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.commit();
        SendUserToLaunchActivity();
    }
}