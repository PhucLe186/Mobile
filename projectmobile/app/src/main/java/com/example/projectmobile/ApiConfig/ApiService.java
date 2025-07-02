package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Search.UserResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/search")
    Call<List<UserResult>> searchUsers(@Query("q") String keyword);
}