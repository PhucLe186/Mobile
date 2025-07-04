package com.example.projectmobile.Notification;

import com.google.gson.annotations.SerializedName;

public class Message {
    private int messageId;

    private int myself;

    @SerializedName("sender_id")
    private int senderId;

    @SerializedName("receiver_id")
    private int receiverId;

    @SerializedName("message")
    private String message;

    @SerializedName("sent_at")
    private String sentAt;

    @SerializedName("is_read")
    private int isRead; // Dùng int để tránh lỗi Expected boolean but was number

    @SerializedName("sender_username")
    private String senderUsername;

    @SerializedName("sender_avatar")
    private String senderAvatar;

    @SerializedName("receiver_username")
    private String receiverUsername;

    @SerializedName("receiver_avatar")
    private String receiverAvatar;

    // Getter
    public int getMessageId() {
        return messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public int getIsRead() {
        return isRead;
    }

    public boolean isRead() {
        return isRead == 1;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getReceiverAvatar() {
        return receiverAvatar;
    }

    public int getMyself() {
        return myself;
    }
}