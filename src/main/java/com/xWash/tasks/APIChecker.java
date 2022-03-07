package com.xWash.tasks;

import com.alibaba.fastjson.JSON;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.QueryResult;
import com.xWash.service.Impl.Distributor;
import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * 从网络API获取数据并写入redis
 */
@Component
@EnableScheduling
public class APIChecker {
    @Autowired
    @Qualifier("distributor")
    private Distributor distributor;
    @Autowired
    @Qualifier("checkPool")
    private Executor checkPoll;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MysqlUtil mysqlUtil;

    @Scheduled(cron = "0/30 * * * * ?")
    public void checkFromAPI() {
        // TODO separate machines by building
        redisUtil.setStr("date", new Date().toString());
        LinkedList<Machine> machines = mysqlUtil.listMachines();

        // TODO thread pool
        machines.forEach(machine -> {
            checkPoll.execute(new Runnable() {
                @Override
                public void run() {
                    QueryResult qr = distributor.check(machine);
                    if (qr.isInit()) {
                        return;
                    }
                    redisUtil.hashSet(machine.getBuilding(), machine.getName(), qr.toJson());
                }
            });
        });
    }

    @PostConstruct
    public void initData() {
        mysqlUtil.updateBuildings();
    }
}
