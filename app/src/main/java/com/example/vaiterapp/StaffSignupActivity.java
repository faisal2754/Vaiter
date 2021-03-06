package com.example.vaiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private ProgressDialog loadingBar;
    private TextView errorText;

    private ImageView iTwitter;
    private ImageView iInstagram;
    private ImageView iFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //what
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_signup);

        eFname = findViewById(R.id.staffSFname);
        eLname = findViewById(R.id.staffSLname);
        eEmail = findViewById(R.id.staffSEmail);
        ePass = findViewById(R.id.staffSPass);
        eCpass = findViewById(R.id.staffSPassC);
        sRes = findViewById(R.id.staffRes);

        btnSignup = findViewById(R.id.btnStaffSignUp);

        loadingBar = new ProgressDialog(this);





        txtAlreadyM = findViewById(R.id.textAlreadyMember);

        txtAlreadyM.setOnClickListener(v -> LoginClick());

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

        List<String> spinnerArray =  new ArrayList<String>();

        spinnerArray.add("Select your restaurant");
        spinnerArray.add("McDonalds");
        spinnerArray.add("Ocean Basket");
        spinnerArray.add("Pizza Hut");
        spinnerArray.add("Lal Qila");
        spinnerArray.add("Nandos");


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
        Intent loginIntent = new Intent(StaffSignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void goToStaffActivity(){
        Intent signupIntent = new Intent(StaffSignupActivity.this, StaffMainActivity.class);
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
        String restaurant = sRes.getSelectedItem().toString();

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
        if (restaurant.equals("Select your restaurant")){
            ((TextView)sRes.getSelectedView()).setError("Error message");
            sRes.requestFocus();
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



        loadingBar.setTitle("Registering account");
        loadingBar.setMessage("Please wait, while we are registering your account.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

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

                        //LoginClick();
                        loadingBar.dismiss();
                        VerificationDialog verificationDialog = new VerificationDialog();
                        verificationDialog.show(getSupportFragmentManager(), "verification dialog");
                        verificationDialog.setCancelable(false);

                    }
                    loadingBar.dismiss();
                    Toast.makeText(StaffSignupActivity.this, js.getString("message"), Toast.LENGTH_LONG).show();
                } catch (IOException | JSONException e) {
                    loadingBar.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingBar.dismiss();
                Toast.makeText(StaffSignupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
