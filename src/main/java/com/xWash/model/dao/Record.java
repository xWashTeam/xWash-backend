package com.xWash.model.dao;

import java.util.Date;

public class Record {
    int id;
    String cookie;
    String building;
    String mode;
    Date createdTime;

    public Record() {
        createdTime = new Date();
        mode = "normal";
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", cookie='" + cookie + '\'' +
                ", building='" + building + '\'' +
                ", mode='" + mode + '\'' +
                ", date=" + createdTime +
                '}';
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
