package com.oni.pixelchat;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;

public class MessageItem {
    public MessageItem(String uid_send, String uid_del, String date, boolean send, boolean picture, String message, int messageID) {
        this.uid_send = uid_send;
        this.uid_del = uid_del;
        this.date = date;
        this.send = send;
        this.picture = picture;
        this.message = message;
        this.messageID = messageID;
    }

    private String uid_send,uid_del,date;
    private boolean send,picture;
    private String message;
    private int messageID;

    public String getUid_send() {
        return uid_send;
    }

    public void setUid_send(String uid_send) {
        this.uid_send = uid_send;
    }

    public String getUid_del() {
        return uid_del;
    }

    public void setUid_del(String uid_del) {
        this.uid_del = uid_del;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public static ArrayList<MessageItem> test(){
        ArrayList<MessageItem> arrayList =new ArrayList<>();
        MessageItem m =new MessageItem("1","2","2",true,false,"AloSend",1);
        arrayList.add(m);
        m =new MessageItem("1","2","2",false,false,"AloDeli",2);
        arrayList.add(m);
        return arrayList;
    }
}
