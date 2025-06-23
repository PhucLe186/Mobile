package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Auth.AuthModel.CheckLogin;
import com.example.projectmobile.Auth.AuthModel.ForgotPasswordRequest;
import com.example.projectmobile.Auth.AuthModel.ForgotPasswordResponse;
import com.example.projectmobile.Auth.AuthModel.LoginRequest;
import com.example.projectmobile.Auth.AuthModel.LoginResponse;
import com.example.projectmobile.Auth.AuthModel.RegisterRequest;
import com.example.projectmobile.Auth.AuthModel.RegisterResponse;
import com.example.projectmobile.Information.User.GetUserInfoReq;
import com.example.projectmobile.Information.User.GetUserInfoRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")//Login API
    Call<LoginResponse> Login(@Body LoginRequest loginModule);
    @POST("auth/register")//Register API
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
    @POST("auth/forgotpassword")//Forgot password API
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);
    @GET("/auth/checklogin")
    Call<CheckLogin> checkLogin(@Header("Authorization") String authHeader);
    @POST("/auth/userinfo")
    Call<GetUserInfoRes> getUserInfo(@Body GetUserInfoReq getUserInfoReq);

}
