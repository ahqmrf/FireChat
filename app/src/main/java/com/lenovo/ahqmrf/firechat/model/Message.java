package com.lenovo.ahqmrf.firechat.model;

import java.util.Date;

/**
 * Created by Lenovo on 2/22/2017.
 */

public class Message {
    private String sentTo;
    private String text;
    private long time;
    private String id;
    private String readByUser;
    private String readBySentTo;
    private int serialId;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public String getReadByUser() {
        return readByUser;
    }

    public void setReadByUser(String readByUser) {
        this.readByUser = readByUser;
    }

    public String getReadBySentTo() {
        return readBySentTo;
    }

    public void setReadBySentTo(String readBySentTo) {
        this.readBySentTo = readBySentTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message() {
        this.time = new Date().getTime();
    }

    public Message(String id, String text, String sentTo, String readByUser, String readBySentTo, int serialId) {
        this.id = id;
        this.text = text;
        this.sentTo = sentTo;
        this.readByUser = readByUser;
        this.readBySentTo = readBySentTo;
        this.serialId = serialId;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
