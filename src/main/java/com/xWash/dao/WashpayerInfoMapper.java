package com.xWash.dao;

import com.xWash.entity.WashpayerInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface WashpayerInfoMapper {
    @Select("SELECT * FROM washpayer_info")
    ArrayList<WashpayerInfo> getAll();
    @Select("SELECT * FROM washpayer_info WHERE qrlink=#{qrlink} LIMIT 1")
    WashpayerInfo getOneByqrlink(@Param("qrlink")String qrlink);

    @Insert(value = "INSERT INTO washpayer_info(name, qrlink, devno) VALUES(#{name},#{qrlink}, #{devno})")
    boolean insertInfo(@Param("name") String name, @Param("qrlink") String qrlink, @Param("devno") String devno);
    @Update(value = "UPDATE washpayer_info SET qrlink=#{qrlink}, devno=#{devno} WHERE name=#{name}")
    boolean updateInfoByName(@Param("name") String name, @Param("qrlink") String qrlink, @Param("devno") String devno);
    @Update(value = "UPDATE washpayer_info SET qrlink=#{qrlink}, devno=#{devno} WHERE qrlink=#{qrlink}")
    boolean updateInfoByQrlink(@Param("qrlink") String qrlink, @Param("devno") String devno);
}
