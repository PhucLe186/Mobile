package com.example.projectmobile.Search.model;

public class VideoItem {
    private int video_id;
    private int user_id;
    private String video_url;
    private String caption;
    private String created_at;
    private String username;
    private String avatar_url;

    public VideoItem(int video_id, String avatar_url, String username, String created_at, String caption, String video_url, int user_id) {
        this.video_id = video_id;
        this.avatar_url = avatar_url;
        this.username = username;
        this.created_at = created_at;
        this.caption = caption;
        this.video_url = video_url;
        this.user_id = user_id;
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

    public String getCreated_at() {
        return created_at;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
