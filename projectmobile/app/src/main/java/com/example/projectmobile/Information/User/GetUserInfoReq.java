package com.example.projectmobile.Information.User;

public class GetUserInfoReq {
    private final int user_id;

    public GetUserInfoReq(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
