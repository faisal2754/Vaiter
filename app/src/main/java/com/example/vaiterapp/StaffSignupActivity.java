package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaiterapp.API.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffSignupActivity extends AppCompatActivity {

    private EditText eFname, eLname, eEmail, ePass, eCpass;
    private Spinner sRes;
    private Button btnSignup;
    public TextView txtAlreadyM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //wtf
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_signup);

        eFname = findViewById(R.id.staffSFname);
        eLname = findViewById(R.id.staffSLname);
        eEmail = findViewById(R.id.staffSEmail);
        ePass = findViewById(R.id.staffSPass);
        eCpass = findViewById(R.id.staffSPassC);

        btnSignup = findViewById(R.id.btnStaffSignUp);

        txtAlreadyM = findViewById(R.id.textAlreadyMember);

        txtAlreadyM.setOnClickListener(v -> LoginClick());

        List<String> spinnerArray =  new ArrayList<String>();

        spinnerArray.add("Select your restaurant");
        spinnerArray.add("McDonalds");
        spinnerArray.add("Ocean Basket");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRes = findViewById(R.id.staffRes);
        sRes.setAdapter(adapter);

//        String selected = sItems.getSelectedItem().toString();
//            if (selected.equals("what ever the option was")) {
//        }

        btnSignup.setOnClickListener(v -> signUp());

    }

    public void LoginClick(){
        Intent staffIntent = new Intent(StaffSignupActivity.this, LoginActivity.class);
        startActivity(staffIntent);
    }

    public void goToCustomerActivity(){
        Intent signupIntent = new Intent(StaffSignupActivity.this, CustomerMainActivity.class);
        signupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signupIntent);
        finish();
    }

    private void signUp(){
        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        String email = eEmail.getText().toString().trim();
        String res = sRes.getSelectedItem().toString();
        String pass = ePass.getText().toString().trim();
        String cpass = eCpass.getText().toString().trim();

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
            eEmail.setError("Please enter a valid email address");
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
        if (cpass.isEmpty()){
            ePass.setError("Please verify your password");
            ePass.requestFocus();
            return;
        }
        if (!cpass.equals(pass)){
            eCpass.setError("Passwords must match");
            eCpass.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .createStaff(fname,lname,email,res,pass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String s = response.body().string();
                    JSONObject js = new JSONObject(s);
                    if (!js.getBoolean("error")){
                        goToCustomerActivity();
                    }
                    Toast.makeText(StaffSignupActivity.this, js.getString("message"), Toast.LENGTH_LONG).show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StaffSignupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
