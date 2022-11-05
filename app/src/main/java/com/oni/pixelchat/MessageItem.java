package com.oni.pixelchat;

public class MessageItem {
    String message;
    boolean send,picture;
    long datetime;

    public MessageItem() {
    }

    public MessageItem(String message, boolean send, boolean picture, long datetime) {
        this.message = message;
        this.send = send;
        this.picture = picture;
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public boolean isPicture() {
        return picture;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
