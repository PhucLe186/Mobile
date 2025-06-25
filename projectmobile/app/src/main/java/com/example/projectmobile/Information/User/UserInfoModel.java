package com.example.projectmobile.Information.User;

public class UserInfoModel {
    private final String username;
    private final String avatar_url;
    private final int number_of_followers;
    private final int number_of_following;
    private final int number_of_likes;
    private final int user_id;


    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUser_id() {return String.valueOf(user_id);}

    public String getNumber_of_likes() {
        return String.valueOf(number_of_likes);
    }

    public String getNumber_of_followers() {
        return String.valueOf(number_of_followers);
    }

    public String getUserName() {
        return username;
    }

    public String getNumber_of_following() {
        return String.valueOf(number_of_following);
    }

    public UserInfoModel(String username, int number_of_followers, int number_of_likes, String avatar_url, int number_of_following, int user_id) {
        this.username = username;
        this.number_of_followers = number_of_followers;
        this.number_of_likes = number_of_likes;
        this.avatar_url = avatar_url;
        this.number_of_following = number_of_following;
        this.user_id = user_id;
    }
}