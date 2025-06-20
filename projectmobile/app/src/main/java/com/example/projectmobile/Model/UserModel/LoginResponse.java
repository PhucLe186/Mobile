package com.example.projectmobile.Model.UserModel;

public class LoginResponse {
    private String message;
    private String token;

    private User user;


    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }


}
