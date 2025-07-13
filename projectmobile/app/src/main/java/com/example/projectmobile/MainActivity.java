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

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.Auth.AuthModel.CheckLogin;
import com.example.projectmobile.CreateVideo.CreateVideoActivity;
import com.example.projectmobile.Information.User.GuestProfileActivity;
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
    private ImageView btnUser, btnInbox, btnHome, btnCart ;
    private String token;
    private Fragment videoFragment;
    private Fragment inboxFragment;
    private Fragment userFragment;
    private Fragment cartFragment;
    private Fragment guestFragment;


    private TextView btnPlus;
    @SuppressLint("WrongViewCast")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = prefs.getString("token", "");

        initFragment();
        initView();
        SetupClick();

        CheckLogin(token);
        // Hiện fragment mặc định
        showFragment(videoFragment);


    }

    private void showFragment(Fragment fragmentToShow) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (videoFragment != null) ft.hide(videoFragment);
        if (inboxFragment != null) ft.hide(inboxFragment);
        if (userFragment != null) ft.hide(userFragment);
        if (cartFragment != null) ft.hide(cartFragment);
        if (guestFragment != null) ft.hide(guestFragment);

        if (!fragmentToShow.isAdded()) {
            ft.add(R.id.fragment_container, fragmentToShow);
        }
        ft.show(fragmentToShow);
        ft.commit();
    }

    private void initFragment() {
        // Khởi tạo fragment 1 lần
        videoFragment = new VideoActivity();
        inboxFragment = new InboxActivity();
        userFragment = new UserInformation_LoggedInProfile();
        cartFragment = new CartActivity();
        guestFragment = new GuestProfileActivity();
    }

    private void SetupClick() {
        findViewById(R.id.btn_cart).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_inbox).setOnClickListener(this);
        findViewById(R.id.btn_user_icon).setOnClickListener(this);

//        loadFragment(new VideoActivity());

        btnAdd.setOnClickListener(v -> {
            if(!token.isEmpty()) startActivity(new Intent( this, CreateVideoActivity.class));
            else Toast.makeText(MainActivity.this, "Cần đăng nhập để tạo video", Toast.LENGTH_LONG).show();
        });
        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent( this, SearchActivity.class));
        });
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        btnSearch=findViewById(R.id.btn_search);
        btnAdd= findViewById(R.id.btn_add_video);
        btnPlus= findViewById(R.id.plus);
        btnHome = findViewById(R.id.img_home);
        btnInbox = findViewById(R.id.img_inbox);
        btnUser = findViewById(R.id.img_user_icon);
        btnCart = findViewById(R.id.img_cart);
    }



    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.btn_user_icon){
            if(token.isEmpty()){
                showFragment(guestFragment);
            }
            else {
                showFragment(userFragment);
            }
        } else if (id == R.id.btn_home) {
            showFragment(videoFragment);
        } else if (id == R.id.btn_inbox) {
                if(token.isEmpty()){
                    showFragment(guestFragment);
            } else {
                    showFragment(inboxFragment);
            }
        } else if (id==R.id.btn_cart) {
            showFragment(cartFragment);
        }

        updateMenuIconColor(id);
        btnSearch.setVisibility(id == R.id.btn_home ? View.VISIBLE : View.GONE);
    }
    @SuppressLint("ResourceAsColor")
    private void updateMenuIconColor(int selectedId) {

        btnHome.setImageResource(R.drawable.home1);
        btnInbox.setImageResource(R.drawable.chat);
        btnUser.setImageResource(R.drawable.user1);
        btnCart.setImageResource(R.drawable.cart1);


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
        } else if (selectedId ==R.id.btn_cart) {
            btnCart.setImageResource(R.drawable.cart2);

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