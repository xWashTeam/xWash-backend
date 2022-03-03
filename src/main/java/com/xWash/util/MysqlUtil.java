package com.xWash.util;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.xWash.mapper.MachineMapper;
import com.xWash.model.dao.Machine;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Component
public class MysqlUtil {
    private final MachineMapper machineMapper;

    public MysqlUtil(MachineMapper machineMapper) {
        this.machineMapper = machineMapper;
    }

    public void updateBuildings() {
        File file = new File(System.getProperty("user.dir") + "/data/machines.json");
        String machinesStr = FileUtil.readUtf8String(file);
        List<Machine> machines = JSON.parseArray(machinesStr, Machine.class);
        machines.forEach(machineMapper::updateMachineByName);
    }

    public void insertBuildings() {
        File file = new File(System.getProperty("user.dir") + "/data/machines.json");
        String machinesStr = FileUtil.readUtf8String(file);
        List<Machine> machines = JSON.parseArray(machinesStr, Machine.class);
        machines.forEach(machineMapper::save);
    }

    public LinkedList<Machine> listMachines() {
        return machineMapper.listMachines();
    }

    public LinkedList<Machine> getMachinesByBuilding(String building) {
        return machineMapper.getMachinesByBuilding(building);
    }
}
