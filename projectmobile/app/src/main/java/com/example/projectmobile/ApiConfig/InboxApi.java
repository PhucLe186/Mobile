package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Notification.model.MessageList;
import com.example.projectmobile.Notification.model.MessageRequest;
import com.example.projectmobile.Notification.model.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InboxApi {

    // Lấy danh sách tin nhắn - trả về MessageResponse chứa data là danh sách Message
    @GET("messages/getmessages")
    Call<MessageResponse> getMessages(@Header("Authorization") String token);
    @GET("messages/fetchChatMessages")
    Call<List<MessageList>> fetchChatMessages(@Header("Authorization") String token, @Query("id") int userId);
    // Gửi tin nhắn mới (sử dụng MessageRequest thay vì Message)
    @POST("messages")
    Call<Void> sendMessage(@Header("Authorization") String token, @Body MessageRequest messageRequest);
}