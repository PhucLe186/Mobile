package com.example.projectmobile.Information.ItemVideo;

import java.util.ArrayList;
import java.util.List;

public class GetVideosItemRes {
    private String message;
    private List<VideoItemHolder> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VideoItemHolder> getData() {
        return data;
    }

    public void setVideos(ArrayList<VideoItemHolder> data) {
        this.data = data;
    }
}
