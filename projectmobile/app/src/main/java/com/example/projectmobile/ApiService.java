package com.example.projectmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {
    @GET("/api/search")
    Call<List<UserResult>> searchUsers(@Query("q") String keyword);
}