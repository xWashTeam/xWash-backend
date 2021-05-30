package com.xWash.entity;

import java.io.Serializable;

public class QueryResult implements Serializable {
    private String name="";
    private MStatus status=MStatus.AVAILABLE;
    private int remainTime=0;
    private String location="";
    private String message = "";
    private static final QueryResult emptyInstance = new QueryResult();

    public static QueryResult getEmptyInstance(){
        return emptyInstance;
    }

    public void setAll(String name, MStatus status, int remainTime, String message) {
        this.name = name;
        this.status = status;
        this.remainTime = remainTime;
        this.message = message;

    }



    @Override
    public String toString() {
        return "QueryResult{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", remainTime=" + remainTime +
                ", loaction='" + location + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toJson() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ",\"status\":\"" + status + "\""+
                ",\"location\":\"" + location + "\""+
                ",\"message\":\"" + message + '\"' +
                ",\"remainTime\":" + remainTime +
                "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MStatus getStatus() {
        return status;
    }

    public void setStatus(MStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
}
