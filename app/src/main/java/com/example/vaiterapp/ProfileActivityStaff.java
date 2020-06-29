package com.example.vaiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivityStaff extends AppCompatActivity {

    //private static final int GALLERY_REQUEST_CODE = 123;

    private String cussName = MainActivity.prf1.getString("fname", "");
    private String email = MainActivity.prf1.getString("email", "");

    private TextView tvName;
    private TextView tvEmail;

    private Button Save;


    //ImageView ProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_staff);

        tvName = findViewById(R.id.name_view1);
        tvEmail = findViewById(R.id.email_view1);

        Save = findViewById(R.id.btnSave1);

        tvName.setText(tvName.getText() + cussName);
        tvEmail.setText(tvEmail.getText() + email);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent staffIntent = new Intent(ProfileActivityStaff.this, StaffMainActivity.class);
                startActivity(staffIntent);
            }
        });



//        ProfilePic = (ImageView) findViewById(R.id.profile_pic);
//
//        ProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Pick a profile picture"), GALLERY_REQUEST_CODE);
//
//            }
//        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
//            Uri imageData = data.getData();
//            ProfilePic.setImageURI(imageData);
//        }
//    }


}
