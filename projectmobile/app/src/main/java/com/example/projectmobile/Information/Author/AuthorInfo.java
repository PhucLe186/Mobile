package com.example.projectmobile.Information.Author;

public class AuthorInfo {
    private int user_id;
    private String avatar_url;
    private String username;
    private int like_count;
    private int follower_count;
    private int following_count;
    private int is_following;

    public AuthorInfo(int user_id, String avatar_url, String username, int like_count, int follower_count, int following_count, int is_following) {
        this.user_id = user_id;
        this.avatar_url = avatar_url;
        this.username = username;
        this.like_count = like_count;
        this.follower_count = follower_count;
        this.following_count = following_count;
        this.is_following = is_following;
    }

    public int getFollowing_count() {
        return following_count;
    }


    public String getUsername() {
        return username;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getAvatar_url() { return avatar_url;}
    public int getLike_count() {
        return like_count;
    }
    public int getFollower_count() {
        return follower_count;
    }

    public int getIs_following() {return is_following;}
    public void setIs_following(int is_following) {this.is_following = is_following;}
}
