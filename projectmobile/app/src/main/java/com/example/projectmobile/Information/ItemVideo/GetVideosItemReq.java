package com.example.projectmobile.Information.ItemVideo;

public class GetVideosItemReq {
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public GetVideosItemReq(int user_id) {
        this.user_id = user_id;
    }
}