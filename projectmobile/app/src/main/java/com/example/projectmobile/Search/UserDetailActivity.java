package com.example.projectmobile.Search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.ApiService;
import com.example.projectmobile.R;
import com.example.projectmobile.Search.model.Keyword;
import com.example.projectmobile.Search.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    LinearLayout Search;
    ImageView onBack;
    private VideoGridAdapter adapter;
    private ApiService api;
    private RecyclerView recyclerView;
    private String keywork;
    private List<String> gridList = new ArrayList<>();//Store user video

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);

        initView();
        settingOnclick();
        keywork = getIntent().getStringExtra("username");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        api= ApiClient.getClient().create(ApiService.class);

        getItemVideo();
    }

    private void settingOnclick() {
        onBack.setOnClickListener(v -> {
            finish();
        });
        Search.setOnClickListener(v -> {
            Intent intent= new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerViewVideos);
        onBack=findViewById(R.id.btnBack);
        Search=findViewById(R.id.editTextSearch);

    }

    private void getItemVideo() {
        api.searchDetail(new Keyword(keywork)).enqueue(new Callback<List<VideoItem>>() {
            @Override
            public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VideoItem> videoList = response.body();

                    adapter = new VideoGridAdapter(UserDetailActivity.this, videoList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<VideoItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
