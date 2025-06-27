package com.example.projectmobile.Comment.Model;

import java.util.ArrayList;
import java.util.List;

public class CommentRes {
    boolean success;

    private List<Comment> data;

    public boolean isSuccess() {
        return success;
    }
    public List<Comment> getData(){
        return data != null ? data : new ArrayList<>();
    }
}