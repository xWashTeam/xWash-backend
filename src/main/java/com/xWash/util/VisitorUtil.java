package com.xWash.util;

import com.xWash.mapper.RecordMapper;
import com.xWash.mapper.UserMapper;
import com.xWash.model.dao.Record;
import com.xWash.model.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Component
public class VisitorUtil {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RecordMapper recordMapper;

    public Cookie generateRandUserCookie() {
        String cookieStr = String.valueOf(UUID.randomUUID())
                .replace("-", "");
        Cookie cookie = new Cookie("user", cookieStr);
        cookie.setMaxAge(60 * 60 * 24 * 365 * 4);
        cookie.setPath("/");
        return cookie;
    }

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public User getUserByCookie(String cookie) {
        return userMapper.getUserByCookie(cookie);
    }

    public void addRecord(Record record) {
        recordMapper.save(record);
    }
}
