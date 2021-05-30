package com.xWash.service.Impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.MStatus;
import com.xWash.service.IChecker;
import com.xWash.util.BuildingFileUtil;
import com.xWash.entity.QueryResult;
import com.xWash.service.IDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.http.HTTPException;
import java.io.File;
import java.util.LinkedHashMap;

@Service
public class Distributor implements IDistributor {


    final UCleanChecker uCleanChecker;
    final UCleanAPPChecker uCleanAPPChecker;
    final SodaChecker sodaChecker;
    final LocationDealer locationDealer;
    Logger logger = LogManager.getLogger("checkerLog");

    public Distributor(@Autowired UCleanChecker uCleanChecker, @Autowired UCleanAPPChecker uCleanAPPChecker, @Autowired SodaChecker sodaChecker, @Autowired LocationDealer locationDealer) {
        this.uCleanChecker = uCleanChecker;
        this.uCleanAPPChecker = uCleanAPPChecker;
        this.sodaChecker = sodaChecker;
        this.locationDealer = locationDealer;
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
        }catch (HTTPException e){
            result.setStatus(MStatus.UNKNOWN);
            result.setMessage("网络错误！请稍后查看");
            logger.warn(json.get("name") + " -> ("+ e.getStatusCode() +") " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据json文件路径查询
     *
     * @param path json文件
     * @return 查询结果
     */
    @Override
    public LinkedHashMap<String, QueryResult> queryByPath(String path) {
        if (!BuildingFileUtil.isExist(path))
            return null;
        String dataVhfJson = ResourceUtil.readUtf8Str(path);
        return queryByJsonString(dataVhfJson);
    }

    @Override
    public LinkedHashMap<String, QueryResult> queryByFile(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return null;
        }
        return queryByJsonString(FileUtil.readUtf8String(file));
    }

    /**
     * 根据传入json字符串查询
     *
     * @param jsonStr string
     * @return 查询结果
     */
    @Override
    public LinkedHashMap<String, QueryResult> queryByJsonString(String jsonStr) {
        JSONObject json = JSONUtil.parseObj(jsonStr, false, true);
        return queryByJsonObject(json);
    }

    /**
     * 使用Hutool的Json处理
     * 最终真正实现查询的方法
     *
     * @param jsonObj json对象
     * @return 查询结果Map key:洗衣机名  value: 洗衣机状态
     */
    @Override
    public LinkedHashMap<String, QueryResult> queryByJsonObject(JSONObject jsonObj) {
        LinkedHashMap<String, QueryResult> map = new LinkedHashMap<>();
        try {
            for (String key :
                    jsonObj.keySet()) {
                JSONObject machineStatusJson = (JSONObject) jsonObj.get(key);
                QueryResult qs = checkDependOnMachineKind(machineStatusJson);  // 发起查询
                qs.setLocation(locationDealer.convertIntoChinese(key));
                map.put(key, qs);
            }
        } catch (Exception e) {
            // TODO exception
        } finally {
            return map;
        }
    }
}
