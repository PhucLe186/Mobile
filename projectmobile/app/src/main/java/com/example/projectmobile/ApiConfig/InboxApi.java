package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Notification.Message;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InboxApi {

    @GET("inbox/messages") // API lấy danh sách tin nhắn
    Call<List<Message>> getInboxMessages();

    @POST("inbox/send") // API gửi tin nhắn
    Call<Void> sendMessage(@Body Message message);
}