package com.xWash.tasks;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.Building;
import com.xWash.entity.QueryResult;
import com.xWash.service.IDistributor;
import com.xWash.service.Impl.WashpayerChecker;
import com.xWash.util.ComparatorsUtil;
import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 从网络API获取数据并写入redis
 */
@Component
@EnableScheduling
public class APIChecker {

    //TODO 将文件内容存为缓存 - 软引用

    @Autowired
    @Qualifier("distributor")
    IDistributor distributor;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MysqlUtil mysqlUtil;

    @Scheduled(cron = "1/30 * * * * ?")
    public void checkFromAPI() {
        // TODO 增加夜晚查询策略
        redisUtil.setStr_Str("date", new Date().toString());
        ArrayList<Building> buildings = mysqlUtil.getAllBuildings();

        for (Building building :
                buildings) {
            Map<String, QueryResult> result = distributor.queryByJsonString(building.getName(), building.getContent());

            if (result != null) {
                result.entrySet()
                        .stream()
                        .sorted(ComparatorsUtil.getComparator(building.getName()))
                        .forEach(entry -> {
                            redisUtil.hashSet(building.getName(), entry.getKey(), entry.getValue());
                        });
            }
        }

    }

    @PostConstruct
    public void initData() {
        mysqlUtil.updateAllBuildings();
        ArrayList<Building> buildings = mysqlUtil.getAllBuildings();
        buildings.forEach((building) -> {
            JSONObject jo = JSONUtil.parseObj(building.getContent());
            for (Map.Entry<String, Object> entry :
                    jo.entrySet()) {
                JSONObject joo = JSONUtil.parseObj(entry.getValue());
                if (joo.get("belong").equals("washpayerChecker")) {
                    String devno = null;
                    String qrLink = (String) joo.get("qrLink");
                    devno = WashpayerChecker.getDevnoByNetwork(qrLink);
                    if (devno != null && !devno.equals("")){
                        mysqlUtil.updateWashpayerInfo(entry.getKey(), qrLink, devno);
                        redisUtil.hashRemove("washpayer_link_to_devno",qrLink);
                    }

                }
            }
        });
    }
}
