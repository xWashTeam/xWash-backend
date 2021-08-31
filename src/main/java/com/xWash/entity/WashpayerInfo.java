package com.xWash.entity;

public class WashpayerInfo {
    int id;
    String name;
    String qrlink;
    String devno;

    @Override
    public String toString() {
        return "WashpayerInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qrlink='" + qrlink + '\'' +
                ", devno='" + devno + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrlink() {
        return qrlink;
    }

    public void setQrlink(String qrlink) {
        this.qrlink = qrlink;
    }

    public String getDevno() {
        return devno;
    }

    public void setDevno(String devno) {
        this.devno = devno;
    }

    public WashpayerInfo(int id, String name, String qrlink, String devno) {
        this.id = id;
        this.name = name;
        this.qrlink = qrlink;
        this.devno = devno;
    }
}
