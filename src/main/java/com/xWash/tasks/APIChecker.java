package com.xWash.tasks;

import com.xWash.model.dao.Machine;
import com.xWash.model.entity.QueryResult;
import com.xWash.service.Impl.Distributor;
import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 从网络API获取数据并写入redis
 */
@Component
@EnableScheduling
public class APIChecker {
    @Autowired
    @Qualifier("distributor")
    Distributor distributor;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MysqlUtil mysqlUtil;

    @Scheduled(cron = "0/30 * * * * ?")
    public void checkFromAPI() {
        // TODO separate machines by building
        redisUtil.setStr_Str("date", new Date().toString());
        LinkedList<Machine> machines = mysqlUtil.listMachines();

        // TODO thread pool
        machines.forEach(machine -> {
            QueryResult qr = distributor.check(machine);
            redisUtil.hashSet(machine.getBuilding(), machine.getName(), qr.toString());
        });
    }

    @PostConstruct
    public void initData() {
//        mysqlUtil.updateAllBuildings();
    }
}
