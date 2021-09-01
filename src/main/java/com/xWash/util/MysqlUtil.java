package com.xWash.util;

import cn.hutool.core.io.FileUtil;
import com.xWash.dao.BuildingMapper;
import com.xWash.dao.WashpayerInfoMapper;
import com.xWash.entity.Building;
import com.xWash.entity.WashpayerInfo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;

@Component
public class MysqlUtil {

    private final BuildingMapper buildingMapper;
    private final WashpayerInfoMapper washpayerInfoMapper;

    public MysqlUtil(BuildingMapper buildingMapper, WashpayerInfoMapper washpayerInfoMapper) {
        this.buildingMapper = buildingMapper;
        this.washpayerInfoMapper = washpayerInfoMapper;
    }

    public ArrayList<Building> getAllBuildings() {
        return buildingMapper.getAll();
    }

    public void updateAllBuildings() {
        File[] buildingFiles = FileUtil.ls("classpath:building_map\\");   // TODO 文件位置解耦

        for (File file :
                buildingFiles) {
            String name = file.getName();
            String content = FileUtil.readUtf8String(file);
            if (!buildingMapper.updateContentByName(name, content)) {
                buildingMapper.insertBuilding(name, content);
            }
        }
    }

    public void updateWashpayerInfo(String name, String qrlink, String devno) {
        if (!washpayerInfoMapper.updateInfoByName(name, qrlink, devno)) {
            washpayerInfoMapper.insertInfo(name, qrlink, devno);
        }
    }

    public ArrayList<WashpayerInfo> getAllWashpayerInfo(){
        return washpayerInfoMapper.getAll();
    }

    public WashpayerInfo getDevnoByQrlink(String qrlink){
        return washpayerInfoMapper.getOneByqrlink(qrlink);
    }
}
