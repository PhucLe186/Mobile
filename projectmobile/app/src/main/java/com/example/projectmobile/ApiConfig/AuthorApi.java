package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Information.Author.AuthorInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthorApi {
    @GET("author/{user_id}")
    Call<AuthorInfo> getAuthorInfor(@Path("user_id") int UserId);
}
