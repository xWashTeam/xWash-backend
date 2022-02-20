package com.xWash.mapper;

import com.xWash.model.dao.Machine;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface MachineMapper {
    @Select(value = "SELECT * FROM machine")
    ArrayList<Machine> listMachines();
    @Select(value = "SELECT * FROM machine WHERE building=#{building}")
    Machine getMachineByBuilding(String building);
    @Update(value = "UPDATE machine" +
        " SET machineId=#{machineId}, location=#{location}, belong=#{belong}, link=#{link}, building=#{building}" +
        " WHERE name=#{name}")
    boolean updateMachineByName(Machine machine);
    @Insert(value = "INSERT INTO machine(name, machine_id, location, belong, link, building)" +
        " VALUES(#{name}, #{machineId}, #{location}, #{belong}, #{link}, #{building})")
    boolean save(Machine machine);
}
