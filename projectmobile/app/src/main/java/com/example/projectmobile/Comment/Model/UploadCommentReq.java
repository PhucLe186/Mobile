package com.example.projectmobile.Comment.Model;

public class UploadCommentReq {
    private int video_id;
    private String comment;
    private String created_at;

    public UploadCommentReq( int video_id, String comment, String created_at) {
        this.video_id = video_id;
        this.comment = comment;
        this.created_at = created_at;
    }
    public String getCreated_at() {
        return created_at;
    }

    public String getComment() {
        return comment;
    }

    public int getVideo_id() {
        return video_id;
    }
}