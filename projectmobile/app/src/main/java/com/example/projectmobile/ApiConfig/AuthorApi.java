package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Information.Author.Author;
import com.example.projectmobile.Information.Author.AuthorInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthorApi {
    @GET("author/{user_id}")
    Call<AuthorInfo> getAuthorInfor(@Header("Authorization") String token,@Path("user_id") int UserId);

    @POST("author/follow")
    Call<Void> followUser(@Header("Authorization") String token, @Body Author author);

    @POST("author/unfollow")
    Call<Void> unfollowUser(@Header("Authorization") String token, @Body Author authorr);
}
