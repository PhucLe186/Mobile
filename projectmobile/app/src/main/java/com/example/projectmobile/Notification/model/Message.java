package com.example.projectmobile.Notification.model;

public class Message {
    private int message_id;
    private int sender_id;
    private int receiver_id;
    private String message;
    private String sent_at;
    private int is_read;

    private String sender_username;
    private String sender_avatar;
    private String receiver_username;
    private String receiver_avatar;

    private int myself;

    public int getMessage_id() {
        return message_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public String getSent_at() {
        return sent_at;
    }

    public int getIs_read() {
        return is_read;
    }

    public String getSender_username() {
        return sender_username;
    }

    public String getSender_avatar() {
        return sender_avatar;
    }

    public String getReceiver_username() {
        return receiver_username;
    }

    public String getReceiver_avatar() {
        return receiver_avatar;
    }

    public int getMyself() {
        return myself;
    }

}