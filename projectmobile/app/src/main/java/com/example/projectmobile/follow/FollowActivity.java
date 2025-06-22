package com.example.projectmobile.follow;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.FollowApi;
import com.example.projectmobile.R;
import com.example.projectmobile.UserAdapter.UserAdapter;
import com.example.projectmobile.model.User;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowActivity extends AppCompatActivity implements UserAdapter.OnFollowClickListener {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    private FollowApi followApi;
    private String currentUserId = "1"; // Bạn có thể thay bằng id thực tế

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        recyclerView = findViewById(R.id.recyclerView);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Retrofit
        followApi = ApiClient.getClient().create(FollowApi.class);

        setupTabs(tabLayout);
        loadFollowingList(); // Mặc định load danh sách following trước
    }

    private void setupTabs(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.following));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.followers));

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
        followApi.getFollowingList(currentUserId).enqueue(new Callback<List<User>>() {
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
        boolean newStatus = !user.isFollowing();
        user.setFollowing(newStatus);
        adapter.notifyItemChanged(position);

        if (newStatus) {
            FollowApi.followUser(user.getUserId()).enqueue(new Callback<Void>() {
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
            FollowApi.unfollowUser(user.getUserId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("Follow", "Unfollow success: " + user.getUserId());
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
