package com.example.projectmobile;

import static com.example.projectmobile.R.id.button3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.UserInformation_LoggedInProfile;

public class GuestProfileActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_guest);

        Button loginButton = findViewById(button3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserIformation_GuestProfile.this, UserInformation_LoggedInProfile.class);
                startActivity(intent);
            }
        });
    }
}