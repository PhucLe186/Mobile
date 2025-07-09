package com.example.projectmobile.Notification.model;

import com.google.gson.annotations.SerializedName;

public class MessageRequest {

    @SerializedName("receiver_id")
    private int receiverId;

    @SerializedName("message")
    private String message;

    // Constructor không tham số (bắt buộc cho Gson khi deserialize)
    public MessageRequest() {}

    // Constructor đầy đủ
    public MessageRequest(int receiverId, String message) {
        this.receiverId = receiverId;
        this.message = message;
    }

    // Getter
    public int getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    // Setter
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}