package com.example.projectmobile.Information.Author;

public class AuthorInfo {
    private int video_id;
    private int user_id;
    private String video_url;
    private String caption;
    private String created_at;
    private int view_count;
    private String avatar_url;
    private String username;
    private int like_count;
    private int save_count;
    private int follower_count;
    private int following_count;
    public int getFollowing_count() {
        return following_count;
    }



    public String getUsername() {
        return username;
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

    public int getView_count() {
        return view_count;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getSave_count() {
        return save_count;
    }

    public int getFollower_count() {
        return follower_count;
    }


}
