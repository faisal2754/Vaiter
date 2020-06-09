package com.example.vaiterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private TextView txtLaunch;
    private EditText eEmail, ePass;
    private Button btnLogin;
    public SharedPreferences sp;
    public static final String MyPREFERENCES = "MyPrefs";
    public boolean isLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        

        txtLaunch = findViewById(R.id.textViewNoAcc);
        txtLaunch.setOnClickListener(v -> SignupClick());

        eEmail = findViewById(R.id.LEmail);
        ePass = findViewById(R.id.LPass);

        sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String email = eEmail.getText().toString();
        String pass = ePass.getText().toString();

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

    private void login(){
        sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String email = eEmail.getText().toString();
        String pass = ePass.getText().toString();

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

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getAPI()
                .userLogin(email, pass);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (!loginResponse.isError()){

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    String e  = eEmail.getText().toString();
                    String p  = ePass.getText().toString();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(email, e);
                    editor.putString(pass, p);

                    editor.commit();
                    goToCustomerActivity();

                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}