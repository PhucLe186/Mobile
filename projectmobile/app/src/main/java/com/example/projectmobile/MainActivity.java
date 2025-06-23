package com.example.projectmobile;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.Auth.AuthModel.CheckLogin;
import com.example.projectmobile.CreateVideo.CreateVideoActivity;
import com.example.projectmobile.Information.GuestProfileActivity;
import com.example.projectmobile.Information.User.UserInformation_LoggedInProfile;
import com.example.projectmobile.Notification.InboxActivity;
import com.example.projectmobile.Search.SearchActivity;
import com.example.projectmobile.Video.VideoActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AuthApi api;
    private ImageView btnSearch;
    private FrameLayout btnAdd;
    private ImageView btnUser, btnInbox, btnHome;
    private String token;


    private TextView btnPlus;
    @SuppressLint("WrongViewCast")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = prefs.getString("token", "");

        initView();
        SetupClick();

        CheckLogin(token);


    }

    private void SetupClick() {
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_inbox).setOnClickListener(this);
        findViewById(R.id.btn_user_icon).setOnClickListener(this);

        loadFragment(new VideoActivity());

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent( this, CreateVideoActivity.class));
        });
        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent( this, SearchActivity.class));
        });
    }

    private void initView() {
        btnSearch=findViewById(R.id.btn_search);
        btnAdd= findViewById(R.id.btn_add_video);
        btnPlus= findViewById(R.id.plus);
        btnHome = findViewById(R.id.img_home);
        btnInbox = findViewById(R.id.img_inbox);
        btnUser = findViewById(R.id.img_user_icon);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        int id =v.getId();

        if(id == R.id.btn_user_icon){
            if(token.isEmpty()){
                selectedFragment= new GuestProfileActivity();
            }
            else {
                selectedFragment= new UserInformation_LoggedInProfile();
            }
        } else if (id == R.id.btn_home) {
            selectedFragment = new VideoActivity();
        }else if (id == R.id.btn_inbox) {
            if(token.isEmpty()){
                selectedFragment= new GuestProfileActivity();
            }
            else {
                selectedFragment = new InboxActivity();
            }
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment);
            updateMenuIconColor(id);
        }
        if (id == R.id.btn_home) {
            btnSearch.setVisibility(View.VISIBLE);
        } else {
            btnSearch.setVisibility(View.GONE);
        }
    }
    @SuppressLint("ResourceAsColor")
    private void updateMenuIconColor(int selectedId) {

        btnHome.setImageResource(R.drawable.home1);
        btnInbox.setImageResource(R.drawable.chat);
        btnUser.setImageResource(R.drawable.user1);

        if(selectedId != R.id.btn_home){
            btnPlus.setTextColor(0xFFFFFFFF);
            btnAdd.setBackgroundResource(R.drawable.button_bg);

        }
        if (selectedId == R.id.btn_home) {
            btnPlus.setTextColor(0xFF000000);
            btnAdd.setBackgroundResource(R.drawable.tiktok_plus_symbol_button_bg);
            btnHome.setImageResource(R.drawable.home);
        } else if (selectedId == R.id.btn_inbox) {
            btnInbox.setImageResource(R.drawable.chat2);
        } else if (selectedId == R.id.btn_user_icon) {
            btnUser.setImageResource(R.drawable.user2);
        }
    }

    private void CheckLogin(String token) {

        if(!token.isEmpty()){
            api= ApiClient.getClient().create(AuthApi.class);
            api.checkLogin("Bearer " + token).enqueue(new Callback<CheckLogin>() {
                @Override
                public void onResponse(Call<CheckLogin> call, Response<CheckLogin> response) {
                    Toast.makeText(MainActivity.this, "bạn đã đăng nhập", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<CheckLogin> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "chua login", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "Token is empty!", Toast.LENGTH_SHORT).show();
        }
    }

}