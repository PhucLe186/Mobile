package com.example.projectmobile.model;
public class User {
    private String userId;
    private String username;
    private String avatarUrl;
    private boolean isFollowing;

    public User(String userId, String username, String avatarUrl, boolean isFollowing) {
        this.userId = userId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.isFollowing = isFollowing;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isFollowing() { return isFollowing; }

    // Setters
    public void setFollowing(boolean following) { isFollowing = following; }
}
