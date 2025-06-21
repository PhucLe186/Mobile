package com.example.projectmobile.Video.model;

public class Video {
    private int video_id;
    private int user_id;
    private String video_url;
    private String caption;
    private String username;
    private String avatar_url;
    private int like_count;
    private int comment_count;
    private int liked;

    public Video(int video_id, int user_id, String video_url, String caption, String username, String avatar_url, int like_count, int comment_count, int liked) {
        this.video_id = video_id;
        this.user_id = user_id;
        this.video_url = video_url;
        this.caption = caption;
        this.username = username;
        this.avatar_url = avatar_url;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.liked = liked;
    }

    public int getVideo_id() {
        return video_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getCaption() {
        return caption;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public int getLiked() {
        return liked;
    }
}
