package com.example.projectmobile.Information.Author;

import static android.content.ContentValues.TAG;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.Information.ItemVideo.VideoItemHolder;
import com.example.projectmobile.Information.User.VideoGridAdapter;
import com.example.projectmobile.Notification.ChatActivity;
import com.example.projectmobile.R;
import com.example.projectmobile.follow.FollowActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class AuthorInformation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoGridAdapter adapter;
    private List<String> gridList = new ArrayList<>();//Store user video
    private ImageView btnBack;
    private ImageView imageViewAvatar;
    private TextView tvName, tvUsername, follower, following, likes, btnFollow, btnMessage;
    private int userId;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_information);

        if (!initUserIdAndToken()) {
            finish();
            return;
        }
        initView();
        setupClick();
        fetchAuthorInfo();
        getUserVideo();
        recyclerView.setLayoutManager(new GridLayoutManager(AuthorInformation.this, 1));//set layout
    }

    private void getUserVideo() {
        ApiHelper.getUserVideo(userId, new ApiHelper.VideoCallback() {
            @Override
            public void onSuccess(List<VideoItemHolder> videoList) {
                if(videoList != null && !videoList.isEmpty()){
                    gridList.clear();
                    for (VideoItemHolder video : videoList) {
                        gridList.add(video.getVideo_url());
                    }
                    adapter = new VideoGridAdapter(AuthorInformation.this, gridList);
                    recyclerView.setAdapter(adapter);
                    videoHandle();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("getUserVideo", "Failed to load videos", t);
            }
        });
    }

    private void fetchAuthorInfo() {
        ApiHelper.FetchAuthorInfo(token, userId, new ApiHelper.UserInfoCallback() {
            @Override
            public void onSuccess(AuthorInfo author) {
                updateUI(author);
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Failed to get user info", t);
            }
        });
    }

    private boolean initUserIdAndToken() {
        userId = getIntent().getIntExtra("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "Không có user_id", Toast.LENGTH_SHORT).show();
            return false;
        }
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = "Bearer " + prefs.getString("token", "");
        return true;
    }
    public void initView(){
        btnBack = findViewById(R.id.btn_back);
        imageViewAvatar=findViewById(R.id.avatar);
        tvName = findViewById(R.id.tv_name);
        tvUsername = findViewById(R.id.tv_username);
        btnFollow = findViewById(R.id.btnFollow);
        btnMessage = findViewById(R.id.btnMessage);
        recyclerView = findViewById(R.id.grid_videos);
        follower = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        likes = findViewById(R.id.likes);
    }
    public void setupClick(){
        btnBack.setOnClickListener(v -> finish());
    }
    private void videoHandle() {
        //Handling Scroll Event
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    adapter.playVisibleVideos(recyclerView);
                }
            }
        });
        //Handle the first video when it's display on screen
        recyclerView.post(()->adapter.playVisibleVideos(recyclerView));
    }
    // mặc định khi chưa ấn follow
    private void showFollowUI(float scale) {
        btnFollow.setText("Follow");
        btnFollow.setBackgroundResource(R.drawable.follow_btn);
        btnFollow.setTextColor(Color.WHITE);
        animateButtonWidth(btnFollow, btnFollow.getWidth(), (int)(150 * scale + 0.5f));
        animateButtonWidth(btnMessage, btnMessage.getWidth(), (int)(150 * scale + 0.5f));
        btnMessage.setText("Nhắn tin");
        btnMessage.setBackgroundResource(R.drawable.inbox_btn);
    }
    // khi ấn follơ set nut follow thành nhắn tin
    private void showFollowingUI(float scale) {
        btnFollow.setText("Nhắn tin");
        btnFollow.setBackgroundResource(R.drawable.inbox_btn);
        btnFollow.setTextColor(Color.BLACK);
        animateButtonWidth(btnFollow, btnFollow.getWidth(), (int)(200 * scale + 0.5f));
        animateButtonWidth(btnMessage, btnMessage.getWidth(), (int)(55 * scale + 0.5f));
        btnMessage.setText("");
        btnMessage.setBackgroundResource(R.drawable.user_setting);
    }
    //animation theo chiều ngang
    private void animateButtonWidth(View button, int startWidth, int endWidth) {
        ValueAnimator animator = ValueAnimator.ofInt(startWidth, endWidth);
        animator.setDuration(100); // 300ms
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = button.getLayoutParams();
            params.width = (int) animation.getAnimatedValue();
            button.setLayoutParams(params);
        });
        animator.start();
    }
    @SuppressLint("MissingInflatedId")
    private void showBottomMenu(AuthorInfo info) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.menu_bottom, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetView.findViewById(R.id.custom).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.unfollow).setOnClickListener(v -> {
            ApiHelper.unFollowUser(token, info, new ApiHelper.FollowCallback() {
                @Override
                public void onSuccess() {
                    bottomSheetDialog.dismiss();
                    // Gọi lại fetchAuthorInfo để lấy dữ liệu mới (is_following, follower count...)
                    fetchAuthorInfo();
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        });
    }
    private void updateUI(AuthorInfo info){
        float scale = getResources().getDisplayMetrics().density;
        tvName.setText(info.getUsername());
        tvUsername.setText("@" + info.getUsername());
        follower.setText(info.getFollower_count() + "\nFollower");
        following.setText(info.getFollowing_count() + "\nĐã follower");
        likes.setText(String.valueOf(info.getLike_count())+ "\nThích" );
        following.setOnClickListener(v -> {
                Intent intent= new Intent(this, FollowActivity.class);
                intent.putExtra("following", "1");
                intent.putExtra("name", info.getUsername());
                intent.putExtra("user_id", String.valueOf(info.getUser_id()));
                startActivity(intent);
        });
        follower.setOnClickListener(v -> {
            Intent intent= new Intent(this, FollowActivity.class);
            intent.putExtra("following", "0");
            intent.putExtra("name", info.getUsername());
            intent.putExtra("user_id", String.valueOf(info.getUser_id()));
            startActivity(intent);
        });
        if(info.getIs_following()==1){
            showFollowingUI(scale);
            btnFollow.setOnClickListener(v -> {
                Intent intent = new Intent(AuthorInformation.this, ChatActivity.class);
                startActivity(intent);
            });
            btnMessage.setOnClickListener(v -> showBottomMenu(info));
        }
        else{
            showFollowUI(scale);
            btnMessage.setOnClickListener(v -> {
                Intent intent = new Intent(AuthorInformation.this, ChatActivity.class);
                startActivity(intent);
            });
            btnFollow.setOnClickListener(v -> {
                info.setIs_following(1);
                updateUI(info);
                ApiHelper.FollowUser(token, info, new ApiHelper.FollowCallback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        showFollowUI(scale);
                        Log.e(TAG, "Failed to get user info", t);
                    }
                });
            });
        }
        Glide.with(this)
                .load(info.getAvatar_url())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .circleCrop()
                .into(imageViewAvatar);
    }
}
