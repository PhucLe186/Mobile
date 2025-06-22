package com.example.projectmobile.Video;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.VideoApi;
import com.example.projectmobile.R;
import com.example.projectmobile.Video.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends Fragment {

    private String token;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private LinearLayoutManager linearLayoutManager;
    private PagerSnapHelper snapHelper;
    private VideoApi getVideo;

    public VideoActivity() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_video, container, false);

        recyclerView = view.findViewById(R.id.recycler_video);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        SharedPreferences prefs = getContext().getSharedPreferences("MyAppPrefs", getContext().MODE_PRIVATE);
        token = prefs.getString("token", "");
        if(token.isEmpty()){
            fetchVideoList(null);
        }else {
            fetchVideoList(token);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && videoAdapter != null) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        videoAdapter.playVideoAtPosition(position);
                    }
                }
            }
        });

        return view;
    }
    private void fetchVideoList(String token) {
        if(token==null){
            getVideo = ApiClient.getClient().create(VideoApi.class);
            getVideo.getVideo().enqueue(new Callback<List<Video>>() {
                @Override
                public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Video> videos = response.body();

                        videoAdapter = new VideoAdapter(videos, getContext());
                        recyclerView.setAdapter(videoAdapter);

                        recyclerView.post(() -> {
                            int position = linearLayoutManager.findFirstVisibleItemPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                videoAdapter.playVideoAtPosition(position);
                            }
                        });
                    } else {
                        Log.e("VideoActivity", "Lỗi lấy video: response null hoặc lỗi");
                    }
                }

                @Override
                public void onFailure(Call<List<Video>> call, Throwable t) {
                    Log.e("VideoActivity", "Lỗi API: " + t.getMessage());
                }
            });
        }
        else{
            getVideo = ApiClient.getClient().create(VideoApi.class);
            getVideo.getVideobyid("Bearer " + token).enqueue(new Callback<List<Video>>() {
                @Override
                public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Video> videos = response.body();

                        videoAdapter = new VideoAdapter(videos, getContext());
                        recyclerView.setAdapter(videoAdapter);

                        recyclerView.post(() -> {
                            int position = linearLayoutManager.findFirstVisibleItemPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                videoAdapter.playVideoAtPosition(position);
                            }
                        });
                    } else {
                        Log.e("VideoActivity", "Lỗi lấy video: response null hoặc lỗi");
                    }
                }

                @Override
                public void onFailure(Call<List<Video>> call, Throwable t) {
                    Log.e("VideoActivity", "Lỗi API: " + t.getMessage());
                }
            });
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoAdapter != null) {
            videoAdapter.releasePlayer();
        }
    }
    public void onPause() {
        super.onPause();
        if (videoAdapter != null) {
            videoAdapter.pausePlayer();
        }
    }
    public void onResume() {
        super.onResume();
        if (videoAdapter != null) {
            videoAdapter.Play();
        }
    }
}
