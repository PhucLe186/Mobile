package com.example.projectmobile.ApiConfig;


import com.example.projectmobile.Video.model.Like;
import com.example.projectmobile.Video.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VideoApi {
    @GET("video/")
    Call<List<Video>> getVideo();
    @GET("video/local")
    Call<List<Video>> getVideobyid(@Header("Authorization") String authHeader);
    @POST("video/likes")
    Call<Like> LAUvideo(@Header("Authorization")String token, @Body Like like);
}
