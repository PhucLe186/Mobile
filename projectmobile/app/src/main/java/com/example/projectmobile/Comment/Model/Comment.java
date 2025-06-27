package com.example.projectmobile.Comment.Model;

public class Comment {
    private String avatar_url;
    private String username;
    private String comment;
    private String created_at;

    public Comment(String avatar_url ,String username, String comment, String comment_time) {
        this.avatar_url=avatar_url;
        this.username = username;
        this.comment = comment;
        this.created_at = comment_time;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUserName() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getComment_time() {
        return created_at;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setComment_time(String comment_time) {
        this.created_at = comment_time;
    }
}