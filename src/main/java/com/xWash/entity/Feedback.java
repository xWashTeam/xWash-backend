package com.xWash.entity;

import java.util.Date;

public class Feedback {
    String text;
    String name;
    String email;
    Date receiveTime;

    public Feedback(){
        // 自动创建
        receiveTime = new Date();
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "text='" + text + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", receiveTime=" + receiveTime +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
