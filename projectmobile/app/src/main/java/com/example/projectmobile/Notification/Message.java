package com.example.projectmobile.Notification;
public class Message {
    private String sender;
    private String content;

    // Constructor mặc định (bắt buộc cho Retrofit/Gson)
    public Message() {
    }

    // Constructor có tham số
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    // Getter và Setter
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}