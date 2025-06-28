package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.follow.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

    public interface FollowApi {

        @GET("users/{userId}/following")
        Call<List<User>> getFollowingList(@Header("Authorization") String token,@Path("userId") String userId);

        @GET("users/{userId}/followers")
        Call<List<User>> getFollowersList(@Path("userId") String userId);

        @POST("users/follow")
        Call<Void> followUser(@Header("Authorization") String token, @Body User user);

       @POST("users/unfollow")
        Call<Void> unfollowUser(@Header("Authorization") String token, @Body User user);

    }

