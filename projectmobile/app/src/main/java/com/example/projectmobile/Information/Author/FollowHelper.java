package com.example.projectmobile.Information.Author;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.FollowApi;
import com.example.projectmobile.Video.CallApi.LikeCall;
import com.example.projectmobile.follow.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowHelper {
    public static void Followvideo(Context context, AuthorInfo info, LikeCall CallBack){
        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = "Bearer " + prefs.getString("token", "");

        User followNew= new User(String.valueOf(info.getUser_id()), null, null, null);

        FollowApi Api= ApiClient.getClient().create(FollowApi.class);
        Api.followUser(token,followNew  ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    CallBack.onSuccess();
                } else {
                    CallBack.onFailure("Lỗi khi bỏ tym");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
