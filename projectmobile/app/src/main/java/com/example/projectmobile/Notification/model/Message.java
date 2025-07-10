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

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSent_at(String sent_at) {
        this.sent_at = sent_at;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public void setSender_username(String sender_username) {
        this.sender_username = sender_username;
    }

    public void setSender_avatar(String sender_avatar) {
        this.sender_avatar = sender_avatar;
    }

    public void setReceiver_username(String receiver_username) {
        this.receiver_username = receiver_username;
    }

    public void setReceiver_avatar(String receiver_avatar) {
        this.receiver_avatar = receiver_avatar;
    }

    public void setMyself(int myself) {
        this.myself = myself;
    }
}