package com.xWash.mapper;

import com.xWash.model.dao.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface UserMapper {
    @Select("SELECT * FROM user")
    ArrayList<User> listUsers();

    @Insert("INSERT INTO user(cookie) VALUES(#{cookie})")
    void addUser(String cookie);

    @Select("SELECT * FROM user WHERE cookie=#{cookie}")
    User getUserByCookie(String cookie);
}
