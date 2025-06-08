package com.example.projectmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PagerSnapHelper snapHelper;
    private VideoAdapter videoAdapter;

    private LinearLayout btnUser, btnInbox;
    LinearLayout btn_user_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupNavigationButtons();
        setupRecyclerView();
    }

    private void setupNavigationButtons() {
        // Nút tìm kiếm
        ImageView btnSearch = findViewById(R.id.btn_search);
        if (btnSearch != null) {
            btnSearch.setOnClickListener(v -> {
                startActivity(new Intent(this, SearchActivity.class));
            });
        }

        // Nút tạo video
        FrameLayout btnCreateVideo = findViewById(R.id.btn_add_video);
        if (btnCreateVideo != null) {
            btnCreateVideo.setOnClickListener(v -> {
                startActivity(new Intent(this, CreateVideoActivity.class));
            });
        }

        // Nút hồ sơ người dùng
        btnUser = findViewById(R.id.btn_user_icon);
        if (btnUser != null) {
            btnUser.setOnClickListener(v -> {
                startActivity(new Intent(this, UserInformation_LoggedInProfile.class));
            });
        }



        // Nút hộp thư (chuyển đến InboxActivity)
        btnInbox = findViewById(R.id.btn_inbox);
        if (btnInbox != null) {
            btnInbox.setOnClickListener(v -> {
                startActivity(new Intent(this, InboxActivity.class));
            });
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_videos);
        linearLayoutManager = new LinearLayoutManager(this);
        snapHelper = new PagerSnapHelper();

        List<String> videoUrls = Arrays.asList(
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        );

        videoAdapter = new VideoAdapter(videoUrls, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoAdapter);
        snapHelper.attachToRecyclerView(recyclerView);

        // Tự động phát video đầu tiên
        playFirstVideo();

        // Lắng nghe sự kiện cuộn để phát video mới
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        videoAdapter.playVideoAtPosition(position);
                    }
                }
            }
        });
    }


    private void playFirstVideo() {
        recyclerView.post(() -> {
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            if (position != RecyclerView.NO_POSITION) {
                videoAdapter.playVideoAtPosition(position);
            }
        });
    }

    private void intentUserInformation() {
        btn_user_icon = findViewById(R.id.btn_user_icon);
        btn_user_icon.setOnClickListener(v->{
            Intent intentUserInformation = new Intent(this, UserInformation_LoggedInProfile.class);
            startActivity(intentUserInformation);
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoAdapter != null) {
            videoAdapter.releasePlayer();
        }
    }
}