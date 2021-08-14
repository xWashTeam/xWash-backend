package com.xWash.entity;

import java.io.Serializable;
import java.util.Date;

public class QueryResult implements Serializable {
    private static final Date initDate = new Date(1);   // 用于表示没有成功更新状态
    private static final QueryResult emptyInstance = new QueryResult();

    private String name="";
    private MStatus status=MStatus.INIT;
    private int remainTime=0;
    private String location="";
    Date date = initDate;  // 最后更新状态的时间
    private String message = "";


    public static QueryResult getEmptyInstance(){
        return emptyInstance;
    }

    public boolean isNormal(){return status == MStatus.AVAILABLE || status == MStatus.USING ;}

    public boolean isInit(){
        return status == MStatus.INIT;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", remainTime=" + remainTime +
                ", location='" + location + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date.toString() + '\'' +
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
                ",\"timestamp\":\"" + date.getTime() + '\"' +
                ",\"date\":\"" + date.toString() + '\"' +
                ",\"remainTime\":" + remainTime +
                "}";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
