package com.example.vaiterapp;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText eFname, eLname, eEmail, ePass, eCpass;
    private Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        eFname = findViewById(R.id.cusSFname);
        eLname = findViewById(R.id.cusSLname);
        eEmail = findViewById(R.id.cusSEmail);
        ePass = findViewById(R.id.cusSPass);

        btnSignup = findViewById(R.id.btnCusSignUp);

        btnSignup.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        /*Toast toast = Toast.makeText(this, fname, Toast.LENGTH_LONG);
        toast.show();*/
        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        String email = eEmail.getText().toString().trim();
        String pass = ePass.getText().toString().trim();

        if (fname.isEmpty()){
            eFname.setError("Please enter a first name");
            eFname.requestFocus();
            return;
        }
        if (lname.isEmpty()){
            eLname.setError("Please enter a last name");
            eLname.requestFocus();
            return;
        }
        if (email.isEmpty()){
            eEmail.setError("Please enter an email address");
            eEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Please enter a valid email");
            eEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            ePass.setError("Please enter a password");
            ePass.requestFocus();
            return;
        }
        if (pass.length() < 6){
            ePass.setError("Password must be 6 or more characters");
            ePass.requestFocus();
            return;
        }
    }



}
