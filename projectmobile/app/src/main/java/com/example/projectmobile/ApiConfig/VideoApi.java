package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Information.ItemVideo.GetVideosItemReq;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemRes;
import com.example.projectmobile.Information.ItemVideo.VideoItemHolder;
import com.example.projectmobile.Video.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VideoApi {
    @GET("video/")
    Call<List<Video>> getVideo();

    @POST("auth/listvideo")
    Call<GetVideosItemRes> getUserVideo(@Body GetVideosItemReq req);
}
