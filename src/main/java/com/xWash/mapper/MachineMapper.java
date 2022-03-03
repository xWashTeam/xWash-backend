package com.xWash.mapper;

import com.xWash.model.dao.Machine;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedList;

public interface MachineMapper {
    @Select(value = "SELECT * FROM machine")
    LinkedList<Machine> listMachines();

    @Select(value = "SELECT * FROM machine WHERE building=#{building}")
    LinkedList<Machine> getMachinesByBuilding(String building);

    @Update(value = "UPDATE machine" +
            " SET machine_id=#{machineId}, location=#{location}, belong=#{belong}, link=#{link}, building=#{building}" +
            " WHERE name=#{name}")
    boolean updateMachineByName(Machine machine);

    @Insert(value = "INSERT INTO machine(name, machine_id, location, belong, link, building)" +
            " VALUES(#{name}, #{machineId}, #{location}, #{belong}, #{link}, #{building})" +
            " ON DUPLICATE KEY UPDATE machine_id=#{machineId}, location=#{location}, belong=#{belong}, link=#{link}, building=#{building}")
    boolean save(Machine machine);
}
