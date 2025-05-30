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

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PagerSnapHelper snapHelper ;

    VideoAdapter videoAdapter;

    LinearLayout btn_user_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        intentSearching();
        intentUserInformation();
        intentCreateVideo();

        List<String> videoUrls = Arrays.asList(
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        );

        recyclerView = findViewById(R.id.recycler_videos);
        videoAdapter = new VideoAdapter(videoUrls, this);
        linearLayoutManager = new LinearLayoutManager(this);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        playFirstVideo();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    videoAdapter.playVideoAtPosition(position);

                    if(position != RecyclerView.NO_POSITION){
                        videoAdapter.playVideoAtPosition(position);
                    }
                }
            }
        });
    }

    private void playFirstVideo() {
        recyclerView.post(()->{
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            if(position != RecyclerView.NO_POSITION){
                videoAdapter.playVideoAtPosition(position);
            }
        });
    }

    private void intentCreateVideo() {
        FrameLayout btn_create_video = findViewById(R.id.btn_add_video);
        btn_create_video.setOnClickListener(v->{
            Intent intentCreateVideo = new Intent(this, CreateVideoActivity.class);
            startActivity(intentCreateVideo);
        });
    }

    private void intentSearching() {
        ImageView btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(v->{
            Intent intentSearching = new Intent(this, SearchActivity.class);
            startActivity(intentSearching);
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
        if(videoAdapter != null) {
            videoAdapter.releasePlayer();
        }
    }
}
