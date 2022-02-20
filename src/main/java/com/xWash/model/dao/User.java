package com.xWash.model.dao;

public class User {
    String cookie;

    @Override
    public String toString() {
        return "User{" +
            "cookie='" + cookie + '\'' +
            '}';
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
