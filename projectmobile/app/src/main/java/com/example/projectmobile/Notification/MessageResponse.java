package com.example.projectmobile.Notification;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MessageResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Message> data;

    // Getter cho success
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Message> getData() {
        return data;
    }

    // Setter nếu cần (tùy bạn)
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
