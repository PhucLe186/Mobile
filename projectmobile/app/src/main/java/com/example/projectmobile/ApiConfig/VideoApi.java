package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Information.ItemVideo.GetVideosItemReq;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemRes;
import com.example.projectmobile.Information.ItemVideo.VideoItemHolder;


import com.example.projectmobile.Information.ItemVideo.GetVideosItemReq;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemRes;
import com.example.projectmobile.Video.model.Like;
import com.example.projectmobile.Video.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VideoApi {


    @POST("auth/listvideo")
    Call<GetVideosItemRes> getUserVideo(@Body GetVideosItemReq req);
    @GET("video/")
    Call<List<Video>> getVideo();

    @POST("auth/listvideo")
    Call<GetVideosItemRes> getUserVideo(@Body GetVideosItemReq req);
    @GET("video/local")
    Call<List<Video>> getVideobyid(@Header("Authorization") String authHeader);
    @POST("video/likes")
    Call<Like> LAUvideo(@Header("Authorization")String token, @Body Like like);
}
