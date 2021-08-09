package com.xWash.service.Impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.http.HttpException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.MStatus;
import com.xWash.service.IChecker;
import com.xWash.util.BuildingFileUtil;
import com.xWash.entity.QueryResult;
import com.xWash.service.IDistributor;
import com.xWash.util.ComparatorsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Service
public class Distributor implements IDistributor {

    final UCleanChecker uCleanChecker;
    final UCleanAPPChecker uCleanAPPChecker;
    final SodaChecker sodaChecker;
    Logger logger = LogManager.getLogger("checkerLog");

    public Distributor(@Autowired UCleanChecker uCleanChecker, @Autowired UCleanAPPChecker uCleanAPPChecker, @Autowired SodaChecker sodaChecker) {
        this.uCleanChecker = uCleanChecker;
        this.uCleanAPPChecker = uCleanAPPChecker;
        this.sodaChecker = sodaChecker;
    }

    public QueryResult checkDependOnMachineKind(JSONObject json) {
        // TODO 解耦
        String belong = ((String) json.get("belong"));
        IChecker checker = null;
        if (belong.equals("UClean")) {
            checker = uCleanChecker;
        } else if (belong.equals("sodalife")) {
            checker = sodaChecker;
        } else if (belong.equals("UCleanAPP")) {
            checker = uCleanAPPChecker;
        }
        assert checker != null;
        QueryResult result = QueryResult.getEmptyInstance();
        try {
            result = checker.checkByQrLink((String) json.get("qrLink"));
        } catch (Exception e) {
            result.setStatus(MStatus.UNKNOWN);
            result.setMessage("网络错误！请稍后查看");
            logger.warn(json.get("name") + " -> (" + e.getCause() + ") " + e.getMessage());
        } finally {
            return result;
        }

    }


    /**
     * 根据传入json字符串查询
     *
     * @param jsonStr string
     * @return 查询结果
     */
    @Override
    public ConcurrentHashMap<String, QueryResult> queryByJsonString(String name, String jsonStr) {
        JSONObject json = JSONUtil.parseObj(jsonStr, false, true);
        return queryByJsonObject(name, json);
    }

    /**
     * 使用Hutool的Json处理
     * 最终真正实现查询的方法
     *
     * @param allMachineJson json对象
     * @return 查询结果Map key:洗衣机名  value: 洗衣机状态
     */
    @Override
    public ConcurrentHashMap<String, QueryResult> queryByJsonObject(String name, JSONObject allMachineJson) {
        ConcurrentHashMap<String, QueryResult> hashMap = new ConcurrentHashMap<>();
        CountDownLatch cdl = new CountDownLatch(allMachineJson.size());


        for (String machineName :
                allMachineJson.keySet()) {
            new Thread(() -> {
                JSONObject machineJson = (JSONObject) allMachineJson.get(machineName);
                QueryResult qs = checkDependOnMachineKind(machineJson);  // 发起查询
                if (!qs.isInit()) {
                    qs.setLocation((String) machineJson.get("location"));
                    qs.setDate(new Date());
                }
                hashMap.put(machineName, qs);
                cdl.countDown();

            }).start();
        }


        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}
