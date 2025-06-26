package com.example.projectmobile.Comment;

public class UploadCommentRes {
    private boolean success;
    private String message;
    private int comment_count;
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public int getComment_count() {return comment_count;}
}