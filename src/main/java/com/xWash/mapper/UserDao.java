package com.xWash.mapper;

import com.xWash.model.dao.Record;

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
