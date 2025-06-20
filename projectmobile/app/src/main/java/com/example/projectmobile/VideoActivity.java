package com.example.projectmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class VideoActivity extends Fragment {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private LinearLayoutManager linearLayoutManager;
    private PagerSnapHelper snapHelper;

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

        List<String> videoUrls = Arrays.asList(
            "https://commondatastorage.googlepis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "https://commondatastorage.googlepis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        );

        videoAdapter = new VideoAdapter(videoUrls, getContext());
        recyclerView.setAdapter(videoAdapter);

        recyclerView.post(() -> {
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            if (position != RecyclerView.NO_POSITION) {
                videoAdapter.playVideoAtPosition(position);
            }
        });

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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoAdapter != null) {
            videoAdapter.releasePlayer();
        }
    }
}
