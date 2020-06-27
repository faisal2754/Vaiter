package com.example.vaiterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.SharedPreferences;

import com.example.vaiterapp.API.LoginResponse;
import com.example.vaiterapp.API.RetrofitClient;

public class LoginActivity extends AppCompatActivity {

    private TextView txtLaunch;
    private ProgressDialog loadingBar;
    private EditText eEmail, ePass;
    private Button btnLogin;
    private CheckBox cStaff;

    SharedPreferences pref;

    public static String currUserName;
    public static int currUserID;

    private ImageView iTwitter;
    private ImageView iInstagram;
    private ImageView iFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //isLoggedIn = false;

        cStaff = findViewById(R.id.LStaff);

        loadingBar = new ProgressDialog(this);

        txtLaunch = findViewById(R.id.textViewNoAcc);
        txtLaunch.setOnClickListener(v -> SignupClick());

        eEmail = findViewById(R.id.LEmail);
        ePass = findViewById(R.id.LPass);

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


        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> login());
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            String e  = eEmail.getText().toString();
//                                            String p  = ePass.getText().toString();
//                                            SharedPreferences.Editor editor = sp.edit();
//                                            editor.putString(email, e);
//                                            editor.putString(pass, p);
//
//                                            editor.commit();
//                                            login();
//                                        }
//                                    });



    }

    public void SignupClick(){
        Intent staffIntent = new Intent(LoginActivity.this, LaunchActivity.class);
        startActivity(staffIntent);

    }

    public void goToCustomerActivity(){
        Intent signupIntent = new Intent(LoginActivity.this, CustomerMainActivity.class);
        signupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signupIntent);
        //finish();
    }

    public void goToStaffActivity(){
        Intent signupIntent = new Intent(LoginActivity.this, StaffMainActivity.class);
        signupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signupIntent);
        //finish();
    }

    private void login(){

        String email = eEmail.getText().toString().trim();
        String pass = ePass.getText().toString().trim();

        pref = getSharedPreferences("user_details",MODE_PRIVATE);


        if (email.isEmpty()){
            eEmail.setError("Please enter your email address");
            eEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            ePass.setError("Please enter your password");
            ePass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Please enter a valid email address");
            eEmail.requestFocus();
            return;
        }
        if (pass.length() < 6){
            ePass.setError("Password must be 6 or more characters");
            ePass.requestFocus();
            return;
        }

        Call<LoginResponse> call;

        if (cStaff.isChecked()){
            call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .staffLogin(email,pass);
        } else {
            call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .customerLogin(email,pass);
        }

        loadingBar.setTitle("Signing into account");
        loadingBar.setMessage("Please wait, while we are signing into your account.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (!loginResponse.isError()){
                    loadingBar.dismiss();

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    User currUser = loginResponse.getUser();
                    currUserName = currUser.getFirstName();

                    if(cStaff.isChecked()){
                        currUserID = currUser.getStaffID();
                    }
                    else {
                        currUserID = currUser.getCustomerID();
                    }


                    //Toast.makeText(LoginActivity.this, ""+currUserID, Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("fname", currUserName);
                    editor.putString("email",email);
                    editor.putString("password",pass);
                    editor.putInt("userID", currUserID);
                    //editor.commit();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    if (cStaff.isChecked()){
                        editor.putBoolean("staff", true);
                        editor.apply();
                        goToStaffActivity();
                    } else {
                        editor.putBoolean("staff", false);
                        editor.apply();
                        goToCustomerActivity();
                    }


                } else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}