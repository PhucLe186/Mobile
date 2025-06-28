package com.example.projectmobile.Information.Author;

import androidx.annotation.NonNull;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthorApi;
import com.example.projectmobile.ApiConfig.VideoApi;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemReq;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemRes;
import com.example.projectmobile.Information.ItemVideo.VideoItemHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper {
    public interface UserInfoCallback {
        void onSuccess(AuthorInfo author);
        void onFailure(Throwable t);
    }
    public interface VideoCallback {
        void onSuccess(List<VideoItemHolder> videoList);
        void onFailure(Throwable t);
    }
    public interface FollowCallback{
        void onSuccess() ;
        void onFailure(Throwable t);
    }

    public static void FollowUser(String token, AuthorInfo info, FollowCallback Callback){
        Author author= new Author(info.getUser_id());

        AuthorApi Api= ApiClient.getClient().create(AuthorApi.class);
        Api.followUser(token,author).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Callback.onSuccess();
                } else {
                    Callback.onFailure(new Exception("Response not successful"));
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Callback.onFailure(t);
            }
        });
    }

    public static void unFollowUser(String token, AuthorInfo info, FollowCallback Callback){
        Author author= new Author(info.getUser_id());

        AuthorApi Api= ApiClient.getClient().create(AuthorApi.class);
        Api.unfollowUser(token,author).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Callback.onSuccess();
                } else {
                    Callback.onFailure(new Exception("Response not successful"));
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Callback.onFailure(t);
            }
        });
    }
    // lấy thông tin authoruser
    public static void FetchAuthorInfo(String token, int userId, UserInfoCallback Callback) {
        AuthorApi getauthor = ApiClient.getClient().create(AuthorApi.class);
        getauthor.getAuthorInfor(token ,userId).enqueue(new Callback<AuthorInfo>() {
            @Override
            public void onResponse(Call<AuthorInfo> call, Response<AuthorInfo> response) {
                if (response.body() != null) {
                   Callback.onSuccess(response.body());
                } else {
                    Callback.onFailure(new Exception("Response not successful"));
                }
            }
            @Override
            public void onFailure(Call<AuthorInfo> call, Throwable t) {
                Callback.onFailure(t);
            }
        });
    }
    public static void getUserVideo( int userId, VideoCallback callback) {
        VideoApi videoApi = ApiClient.getClient().create(VideoApi.class);
        GetVideosItemReq request =  new GetVideosItemReq(userId);
        videoApi.getUserVideo(request).enqueue(new Callback<GetVideosItemRes>() {
            @Override
            public void onResponse(@NonNull Call<GetVideosItemRes> call, @NonNull Response<GetVideosItemRes> response) {
                if(response.isSuccessful()&&response.body() != null ){
                    List<VideoItemHolder> videoList = response.body().getData();
                        callback.onSuccess(videoList);
                }
                else{//if fail in get user's video
                    callback.onFailure(new Exception("Response not successful or body null"));
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetVideosItemRes> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }


}
