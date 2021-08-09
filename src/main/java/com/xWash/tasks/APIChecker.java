package com.xWash.tasks;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.Building;
import com.xWash.entity.QueryResult;
import com.xWash.service.Impl.Distributor;
import com.xWash.util.BuildingFileUtil;
import com.xWash.util.ComparatorsUtil;
import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 从网络API获取数据并写入redis
 */
@Component
@EnableScheduling
public class APIChecker {

    //TODO 将文件内容存为缓存 - 软引用

    @Autowired
    Distributor distributor;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MysqlUtil mysqlUtil;

    @Scheduled(cron = "1/10 * * * * ?")
    public void checkFromAPI(){
        // TODO 增加夜晚查询策略
        redisUtil.setStr_Str("date",new Date().toString());

        ArrayList<Building> buildings = mysqlUtil.getAllBuildings();

        for (Building building:
             buildings) {
            Map<String, QueryResult> result = distributor.queryByJsonString(building.getName(),building.getContent());

            if (result != null){
                result.entrySet()
                        .stream()
                        .sorted(ComparatorsUtil.getComparator(building.getName()))
                        .forEach(entry->{
                            redisUtil.hashSet(building.getName(),entry.getKey(),entry.getValue());
                        });
            }
        }

    }
}
