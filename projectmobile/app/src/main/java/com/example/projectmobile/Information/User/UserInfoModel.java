package com.example.projectmobile.Information.User;

public class UserInfoModel {
    private final String username;
    private final String avatar_url;
    private final int number_of_followers;
    private final int number_of_likes;


    public String getAvatar_url() {
        return avatar_url;
    }

    public String getNumber_of_likes() {
        return String.valueOf(number_of_likes);
    }

    public String getNumber_of_followers() {
        return String.valueOf(number_of_followers);
    }

    public String getUserName() {
        return username;
    }

    public UserInfoModel(String username, int number_of_followers, int number_of_likes, String avatar_url) {
        this.username = username;
        this.number_of_followers = number_of_followers;
        this.number_of_likes = number_of_likes;
        this.avatar_url = avatar_url;
    }
}
