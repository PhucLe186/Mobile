package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Comment.CommentAdapter;
import com.example.projectmobile.Comment.Model.CommentRes;
import com.example.projectmobile.Comment.Model.GetUserInfoRes;
import com.example.projectmobile.Comment.Model.UploadCommentReq;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentApi {
    @GET("comment/getcomments")//get exited comments
    Call<CommentRes> getComments(@Query("video_id") int videoId);

    @GET("comment/getuserinfo")// get user info
    Call<GetUserInfoRes> getInfo(@Header("Authorization") String token);

    @POST("comment/uploadcomment")//send user comment to server
    Call<CommentAdapter.UploadCommentRes> uploadComment(@Header("Authorization") String token, @Body UploadCommentReq uploadCommentReq);

}