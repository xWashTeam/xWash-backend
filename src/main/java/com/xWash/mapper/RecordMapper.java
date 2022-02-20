package com.xWash.mapper;

import com.xWash.model.dao.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordMapper {
    @Select("SELECT * FROM record")
    List<Record> listRecords();
    @Select("SELECT * FROM record WHERE cookie=#{cookie}")
    List<Record> getRecordsByCookie(String cookie);
    @Insert("INSERT INTO record(cookie, building, date, mode)" +
        " VALUES(#{cookie}, #{building}, #{date}, #{mode})")
    boolean save(Record record);
}
