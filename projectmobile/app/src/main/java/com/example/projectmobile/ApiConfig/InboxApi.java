package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Notification.MessageRequest;
import com.example.projectmobile.Notification.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InboxApi {

    // Lấy danh sách tin nhắn - trả về MessageResponse chứa data là danh sách Message
    @GET("messages")
    Call<MessageResponse> getMessages(@Header("Authorization") String token);

    // Gửi tin nhắn mới (sử dụng MessageRequest thay vì Message)
    @POST("messages")
    Call<Void> sendMessage(@Header("Authorization") String token, @Body MessageRequest messageRequest);
}
