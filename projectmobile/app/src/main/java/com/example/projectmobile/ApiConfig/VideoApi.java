package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Video.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoApi {
    @GET("video/")
    Call<List<Video>> getVideo();
}
