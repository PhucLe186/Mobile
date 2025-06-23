package com.example.projectmobile.Video.CallApi;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.VideoApi;
import com.example.projectmobile.Video.model.Like;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeHelper {
    public static void LAUvideo(Context context, int video_id, LikeCall CallBack){
        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = "Bearer " + prefs.getString("token", "");

        VideoApi Api= ApiClient.getClient().create(VideoApi.class);
        Api.LAUvideo(token, new Like(video_id)).enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Call<Like> call, Response<Like> response) {
                if (response.isSuccessful()) {
                    CallBack.onSuccess();
                } else {
                    CallBack.onFailure("Lỗi khi bỏ tym");
                }
            }

            @Override
            public void onFailure(Call<Like> call, Throwable t) {

            }
        });


    }
}
