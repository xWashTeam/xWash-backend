package com.xWash.dao;

import com.xWash.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    ArrayList<String> getAllUsers();
    void addUser(String cookie);
    String getUserByCookie(String cookie);
    List<Record> getAllRecord();
    List<Record> getUserRecords(String cookie);
    void addRecord(Record record);
}
