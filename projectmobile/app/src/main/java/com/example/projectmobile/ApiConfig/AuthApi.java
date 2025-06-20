package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Auth.AuthModule.CheckLogin;
import com.example.projectmobile.Auth.AuthModule.LoginRequest;
import com.example.projectmobile.Auth.AuthModule.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<LoginResponse> Login(@Body LoginRequest loginModule);

    @GET("/auth/checklogin")
    Call<CheckLogin> checkLogin(@Header("Authorization") String authHeader);
}
