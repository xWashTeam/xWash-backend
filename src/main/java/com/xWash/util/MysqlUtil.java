package com.xWash.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.dao.BuildingMapper;
import com.xWash.entity.Building;
import com.xWash.entity.QueryResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class MysqlUtil {

    private final BuildingMapper buildingMapper;

    public MysqlUtil(BuildingMapper buildingMapper) {
        this.buildingMapper = buildingMapper;
    }

    public ArrayList<Building> getAllBuildings(){
        return buildingMapper.getAll();
    }

    public void updateAllBuildings(){
        File[] buildingFiles = FileUtil.ls("classpath:building_map\\");   // TODO 文件位置解耦

        for (File file :
                buildingFiles) {
            String name = file.getName();
            String content = FileUtil.readUtf8String(file);
            if (!buildingMapper.updateContentByName(name,content)) {
                buildingMapper.insertBuilding(name,content);
            }
         }
    }
}
