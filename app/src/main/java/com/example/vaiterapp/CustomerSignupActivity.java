package com.example.vaiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiterapp.API.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerSignupActivity extends AppCompatActivity {

    private EditText eFname, eLname, eEmail, ePass, eCpass;
    private Button btnSignup;
    private TextView txtAM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        eFname = findViewById(R.id.cusSFname);
        eLname = findViewById(R.id.cusSLname);
        eEmail = findViewById(R.id.cusSEmail);
        ePass = findViewById(R.id.cusSPass);
        eCpass = findViewById(R.id.cusSPassC);

        btnSignup = findViewById(R.id.btnCusSignUp);
        txtAM = findViewById(R.id.textAlreadyMember);

        btnSignup.setOnClickListener(v -> signUp());
        txtAM.setOnClickListener(v -> LoginClick());
    }

    public void LoginClick(){
        Intent staffIntent = new Intent(CustomerSignupActivity.this, LoginActivity.class);
        startActivity(staffIntent);

    }

    public void goToCustomerActivity(){
        Intent signupIntent = new Intent(CustomerSignupActivity.this, CustomerMainActivity.class);
        signupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signupIntent);
        finish();
    }

    private void signUp() {
        /*Toast toast = Toast.makeText(this, fname, Toast.LENGTH_LONG);
        toast.show();*/
        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        String email = eEmail.getText().toString().trim();
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
                .createCustomer(fname, lname, email, pass);

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
                    Toast.makeText(CustomerSignupActivity.this, js.getString("message"), Toast.LENGTH_LONG).show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CustomerSignupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());
            }
        });
    }

}
