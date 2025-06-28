package com.example.projectmobile.Information.User;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.ApiConfig.VideoApi;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemReq;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemRes;
import com.example.projectmobile.Information.ItemVideo.VideoItemHolder;
import com.example.projectmobile.Module.DecodeToken;
import com.example.projectmobile.R;
import com.example.projectmobile.Setting.SettingActivity;
import com.example.projectmobile.follow.FollowActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInformationLoggedInActivity extends AppCompatActivity {

    private LinearLayout layout1, layout2, layout3;
    private TextView usernameText, followersText, followingText, likesText;
    private ImageView menuButton, imgAvatar;
    private RecyclerView recyclerView;
    private VideoGridAdapter adapter;
    private List<String> gridList = new ArrayList<>(); // Store user video
    private VideoApi videoApi;
    private AuthApi authApi;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infomation_logged_in);

        initView();

        getUserIdFromToken();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // set layout
        getUserVideo(); // get video from server then store it into gridList
        getUserInfo(); // get user info to display on screen

        menuButton.setOnClickListener(v -> showBottomMenu());
    }

    private void initView() {
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        usernameText = findViewById(R.id.txt_username);
        followingText = findViewById(R.id.txt_followers_number);
        followersText = findViewById(R.id.txt_following_number);
        likesText = findViewById(R.id.txt_like_number);
        menuButton = findViewById(R.id.img_menu);
        imgAvatar = findViewById(R.id.img_avatar);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void showBottomMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_menu, null);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        bottomSheetView.findViewById(R.id.menu_tiktok_studio).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.menu_so_du).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.menu_qr).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.menu_settings).setOnClickListener(v -> {
            startActivity(new Intent(this, SettingActivity.class));
            bottomSheetDialog.dismiss();
        });
    }

    private void getUserInfo() {
        authApi = ApiClient.getClient().create(AuthApi.class);
        GetUserInfoReq request = new GetUserInfoReq(user_id);
        Log.d(TAG, "getUserInfo:" + request);
        authApi.getUserInfo(request).enqueue(new Callback<GetUserInfoRes>() {
            @Override
            public void onResponse(@NonNull Call<GetUserInfoRes> call, @NonNull Response<GetUserInfoRes> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    usernameText.setText(response.body().getData().getUserName());
                    followingText.setText(response.body().getData().getNumber_of_following());
                    followersText.setText(response.body().getData().getNumber_of_followers());
                    likesText.setText(response.body().getData().getNumber_of_likes());
                    Glide.with(UserInformationLoggedInActivity.this)
                            .load(response.body().getData().getAvatar_url())
                            .placeholder(R.drawable.user)
                            .circleCrop()
                            .error(R.drawable.user)
                            .into(imgAvatar);
                    layout1.setOnClickListener(v -> {
                        Intent intent = new Intent(UserInformationLoggedInActivity.this, FollowActivity.class);
                        intent.putExtra("following", "1");
                        intent.putExtra("name", response.body().getData().getUserName());
                        intent.putExtra("user_id", response.body().getData().getUser_id());
                        startActivity(intent);
                    });
                    layout2.setOnClickListener(v -> {
                        Intent intent = new Intent(UserInformationLoggedInActivity.this, FollowActivity.class);
                        intent.putExtra("following", "0");
                        intent.putExtra("name", response.body().getData().getUserName());
                        intent.putExtra("user_id", response.body().getData().getUser_id());
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<GetUserInfoRes> call, Throwable t) {
            }
        });
    }

    private void getUserIdFromToken() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = prefs.edit();
        String token = prefs.getString("token", null);
        assert token != null;
        user_id = Integer.parseInt(new DecodeToken().getUserIdFromToken(token));
    } // handling decode token to get user_id

    private void videoHandle() {
        // Handling Scroll Event
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    adapter.playVisibleVideos(recyclerView);
                }
            }
        });

        // Handle the first video when it's display on screen
        recyclerView.post(() -> adapter.playVisibleVideos(recyclerView));
    }

    private void getUserVideo() {
        videoApi = ApiClient.getClient().create(VideoApi.class);
        GetVideosItemReq request = new GetVideosItemReq(user_id);
        videoApi.getUserVideo(request).enqueue(new Callback<GetVideosItemRes>() {
            @Override
            public void onResponse(@NonNull Call<GetVideosItemRes> call, @NonNull Response<GetVideosItemRes> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null; // ensure response body is not null
                    List<VideoItemHolder> videoList = response.body().getData();
                    if (videoList != null && !videoList.isEmpty()) {
                        for (VideoItemHolder video : videoList) {
                            gridList.add(video.getVideo_url());
                            Log.d("getUserVideo", gridList.get(0));
                            adapter = new VideoGridAdapter(UserInformationLoggedInActivity.this, gridList);
                            recyclerView.setAdapter(adapter); // set adapter
                            videoHandle(); // Handling video when it's display on screen
                        }
                    }
                } else { // if fail in get user's video
                    Log.d("getUserVideo", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetVideosItemRes> call, @NonNull Throwable t) {
            }
        });
    }
}