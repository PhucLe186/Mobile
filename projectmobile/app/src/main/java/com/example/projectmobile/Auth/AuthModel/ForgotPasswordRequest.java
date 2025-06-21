package com.example.projectmobile.Auth.AuthModel;

public class ForgotPasswordRequest {
    private String email;
    private String username;

    public ForgotPasswordRequest(String email, String username){
        this.email = email;
        this.username = username;

    }
}