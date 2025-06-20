package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Model.UserModel.LoginRequest;
import com.example.projectmobile.Model.UserModel.LoginResponse;
import com.example.projectmobile.Model.UserModel.RegisterRequest;
import com.example.projectmobile.Model.UserModel.RegisterResponse;

import retrofit2.http.Body;
import retrofit2.Call;
import retrofit2.http.POST;

public interface AuthAPIService {
    @POST("api/auth/login")//Login API
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/register")//Register API
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
}
