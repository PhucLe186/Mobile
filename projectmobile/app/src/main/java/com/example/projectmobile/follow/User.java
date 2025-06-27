package com.example.projectmobile.follow;
public class User {
    private String id;
    private String username;
    private String avatarUrl;
    private String follow_status;

    public User(String id, String username, String avatarUrl, String follow_status) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.follow_status = follow_status;
    }

    // Getters

    public String getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(String follow_status) {
        this.follow_status = follow_status;
    }

    public void setId(String id) {this.id = id;}

    public void setUsername(String username) {this.username = username;}

    public void setAvatarUrl(String avatarUrl) {this.avatarUrl = avatarUrl;}
}
