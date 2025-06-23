package com.example.projectmobile.Comment;

public class Comment {
    private String avatar_url;
    private String userName;
    private String comment;

    private String comment_time;

    public Comment(String avatar_url ,String userName, String comment, String comment_time) {
        this.avatar_url=avatar_url;
        this.userName = userName;
        this.comment = comment;
        this.comment_time = comment_time;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
}
