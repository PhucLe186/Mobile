package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Search.model.Keyword;
import com.example.projectmobile.Search.model.UserResult;
import com.example.projectmobile.Search.model.VideoItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/search")
    Call<List<UserResult>> searchUsers(@Query("q") String keyword);

    @POST("api/searchdetail")
    Call<List<VideoItem>> searchDetail(@Body Keyword keyword);
}