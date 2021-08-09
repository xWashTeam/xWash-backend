package com.xWash.dao;

import com.xWash.entity.Building;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface BuildingMapper {
    @Select(value = "SELECT * FROM building")
    public ArrayList<Building> getAll();
    @Select(value = "SELECT content FROM building WHERE name=#{name}")
    public String getContentByName(@Param("name") String name);
    @Update(value = "UPDATE building SET content=#{content} WHERE name=#{name}")
    public boolean updateContentByName(@Param("name") String name, @Param("content") String content);
    @Insert(value = "INSERT INTO building(name, content) VALUES(#{name},#{content})")
    public boolean insertBuilding(@Param("name") String name, @Param("content")String content);
}
