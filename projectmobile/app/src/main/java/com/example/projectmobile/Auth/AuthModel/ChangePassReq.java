package com.example.projectmobile.Auth.AuthModel;

public class ChangePassReq {
    private String oldPass;
    private String newPass;
    public ChangePassReq(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }
}