package com.example.projectmobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserInformation_LoggedInProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infomation_logged_in);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView usernameText = findViewById(R.id.usernameText);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView followersText = findViewById(R.id.followersText);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView likesText = findViewById(R.id.likesText);

        // Giả lập dữ liệu người dùng
        usernameText.setText("@user123");
        followersText.setText("Followers: 12.3K");
        likesText.setText("Likes: 48.7K");
    }
}