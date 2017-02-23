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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message() {
        this.time = new Date().getTime();
    }

    public Message(String id, String text, String sentTo) {
        this.id = id;
        this.text = text;
        this.sentTo = sentTo;
        this.time = new Date().getTime();
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
