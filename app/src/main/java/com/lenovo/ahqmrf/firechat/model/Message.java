package com.lenovo.ahqmrf.firechat.model;

import java.util.Date;

/**
 * Created by Lenovo on 2/22/2017.
 */

public class Message {
    private String user;
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

    public Message(String id, String text) {
        this.id = id;
        this.text = text;
        this.time = new Date().getTime();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
