package com.example.projectmobile;

public class Test {

    private int user_id;
    private String username;
    private String email;
    private String password;
    private String avatar_url;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private String created_at;

    public Test(int user_id, String username, String email, String avatar_url, String password, String created_at) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.avatar_url = avatar_url;
        this.password = password;
        this.created_at = created_at;
    }





}
