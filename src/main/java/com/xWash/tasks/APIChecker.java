package com.xWash.tasks;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.QueryResult;
import com.xWash.service.Impl.Distributor;
import com.xWash.util.BuildingFileUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

    @Scheduled(cron = "1/30 * * * * ?")
    public void checkFromAPI() throws UnsupportedEncodingException {
        // TODO 增加夜晚查询策略
        redisUtil.setStr_Str("date",new Date().toString());
        // 查找building_map下的所有json文件进行查询
        File[] buildingFiles = FileUtil.ls("classpath:building_map\\");   // TODO 文件位置解耦

        for (File file :
                buildingFiles) {
            LinkedHashMap<String, QueryResult> result = distributor.queryByFile(file);

//            for (String key :
//                    result.keySet()) {
//                System.out.println("[APIChecker] QueryResult -> "+ result.get(key).toString());
//            }

            // 遍历结果集
            if (result != null) {
                StringBuilder resultStr = new StringBuilder("");
                JSONObject jsonObject = JSONUtil.parseObj(result,false,true);
                resultStr.append(jsonObject);
                redisUtil.setStr_Str(file.getName().split("\\.")[0], resultStr.toString());
            }
        }
    }
}
