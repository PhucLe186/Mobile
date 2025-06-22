package com.example.projectmobile.ApiConfig;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;
import com.example.projectmobile.follow.User;

    public interface FollowApi {

        @GET("users/{userId}/following")
        Call<List<User>> getFollowingList(@Path("userId") String userId);

        @GET("users/{userId}/followers")
        Call<List<User>> getFollowersList(@Path("userId") String userId);

        @POST("users/{userId}/follow")
        static Call<Void> followUser(@Path("userId") String userId) {
            return null;
        }

        @DELETE("users/{userId}/follow")
        static Call<Void> unfollowUser(@Path("userId") String userId) {
            return null;
        }

    }

