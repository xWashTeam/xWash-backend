package com.xWash.model.dao;

public class Machine {
    int id;
    String name;
    String machineId;
    String location;
    String belong;
    String building;
    String link;

    @Override
    public String toString() {
        return "Building{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", machineId='" + machineId + '\'' +
            ", location='" + location + '\'' +
            ", belong='" + belong + '\'' +
            ", link='" + link + '\'' +
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

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
