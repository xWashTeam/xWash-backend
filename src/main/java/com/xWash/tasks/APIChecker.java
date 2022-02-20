//package com.xWash.tasks;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.xWash.model.dao.Machine;
//import com.xWash.service.intf.IDistributor;
//import com.xWash.service.Impl.WashpayerChecker;
//import com.xWash.util.MysqlUtil;
//import com.xWash.util.RedisUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
///**
// * 从网络API获取数据并写入redis
// */
//@Component
//@EnableScheduling
//public class APIChecker {
//    @Autowired
//    @Qualifier("distributor")
//    IDistributor distributor;
//    @Autowired
//    RedisUtil redisUtil;
//    @Autowired
//    MysqlUtil mysqlUtil;
//
//    @Scheduled(cron = "1/30 * * * * ?")
//    public void checkFromAPI() {
//        // TODO 增加夜晚查询策略
//        redisUtil.setStr_Str("date", new Date().toString());
//        ArrayList<Machine> buildings = mysqlUtil.getAllBuildings();
//
//        for (Machine building :
//                buildings) {
//            distributor.queryByJsonString(building.getName(), building.getContent());
//        }
//
//    }
//
//    @PostConstruct
//    public void initData() {
//        mysqlUtil.updateAllBuildings();
//        ArrayList<Machine> buildings = mysqlUtil.getAllBuildings();
//        buildings.forEach((building) -> {
//            JSONObject jo = JSONUtil.parseObj(building.getContent());
//            for (Map.Entry<String, Object> entry :
//                    jo.entrySet()) {
//                JSONObject joo = JSONUtil.parseObj(entry.getValue());
//                if (joo.get("belong").equals("washpayerChecker")) {
//                    String devno = null;
//                    String qrLink = (String) joo.get("qrLink");
//                    devno = WashpayerChecker.getDevnoByNetwork(qrLink);
//                    if (devno != null && !devno.equals("")){
//                        mysqlUtil.updateWashpayerInfo(entry.getKey(), qrLink, devno);
//                        redisUtil.hashRemove("washpayer_link_to_devno",qrLink);
//                    }
//
//                }
//            }
//        });
//    }
//}
