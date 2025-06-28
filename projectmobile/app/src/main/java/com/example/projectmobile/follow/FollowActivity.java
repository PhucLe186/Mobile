package com.example.projectmobile.follow;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.FollowApi;
import com.example.projectmobile.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowActivity extends AppCompatActivity implements UserAdapter.OnFollowClickListener {

    private String token;
    private ImageView onback;
    private TextView headername;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    private  String username;
    private FollowApi followApi;
    private String currentUserId;
    private String styles;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        initView();
        listener();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUserId=getIntent().getStringExtra("user_id");
        username = getIntent().getStringExtra("name");
        styles=getIntent().getStringExtra("following");
        // Khởi tạo Retrofit
        followApi = ApiClient.getClient().create(FollowApi.class);
        setupTabs(tabLayout);
        //lấy token
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = prefs.getString("token", "");


        if ("1".equals(styles)) {
            tabLayout.selectTab(tabLayout.getTabAt(0));  // Chọn tab Following
            loadFollowingList();
        } else {
            tabLayout.selectTab(tabLayout.getTabAt(1));  // Chọn tab Followers
            loadFollowersList();
        }
    }

    private void listener() {
        onback.setOnClickListener(v -> finish());

    }

    private void initView() {
        onback=findViewById(R.id.onback);
        headername=findViewById(R.id.header);
        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void setupTabs(TabLayout tabLayout) {
        headername.setText(username);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadFollowingList();
                } else {
                    loadFollowersList();
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadFollowingList() {
        followApi.getFollowingList("Bearer " + token ,currentUserId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUserList(response.body());
                } else {
                    Log.e("Follow", "Load following failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Follow", "Error: " + t.getMessage());
            }
        });
    }

    private void loadFollowersList() {
        followApi.getFollowersList(currentUserId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUserList(response.body());
                } else {
                    Log.e("Follow", "Load followers failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Follow", "Error: " + t.getMessage());
            }
        });
    }

    private void updateUserList(List<User> users) {
        this.userList = users;
        adapter = new UserAdapter(userList, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFollowClick(int position, User user) {

        String newStatus = user.getFollow_status();
        user.setFollow_status("Bạn bè");
        adapter.notifyItemChanged(position);

        if ("Follow".equals(newStatus)) {
            followApi.followUser("Bearer " + token, user).enqueue(new Callback<Void>()  {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("Follow", "Follow success: " + user.getUserId());
                    } else {
                        Log.e("Follow", "Follow failed");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Follow", "Follow error: " + t.getMessage());
                }
            });

        } else {
            newStatus = "Follow";
            user.setFollow_status(newStatus);
            adapter.notifyItemChanged(position);

            followApi.unfollowUser("Bearer " + token, user).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("Follow", "Unfollow success: " + currentUserId);
                    } else {
                        Log.e("Follow", "Unfollow failed");
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Follow", "Unfollow error: " + t.getMessage());
                }
            });
        }
    }
}
