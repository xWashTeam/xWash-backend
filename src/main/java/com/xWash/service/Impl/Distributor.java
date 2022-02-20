//package com.xWash.service.Impl;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.xWash.model.entity.MStatus;
//import com.xWash.service.intf.IChecker;
//import com.xWash.model.entity.QueryResult;
//import com.xWash.service.intf.IDistributor;
//import com.xWash.util.RedisUtil;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service("distributor")
//public class Distributor implements IDistributor {
//
//    private final BeanFactory beanFactory;
//    @Autowired
//    RedisUtil redisUtil;
//
//    @Autowired
//    public Distributor(BeanFactory beanFactory) {
//        this.beanFactory = beanFactory;
//    }
//
//    public QueryResult checkDependOnMachineKind(JSONObject json) {
//        // TODO 解耦
//        String belong = ((String) json.get("belong"));
//        IChecker checker = null;
//        try{
//            checker = (IChecker) beanFactory.getBean(belong);
//        }catch (Exception e){
//            return new QueryResult();
//        }
//
//        QueryResult result = QueryResult.getEmptyInstance();
//        try {
//            result = checker.checkByQrLink((String) json.get("qrLink"));
//        } catch (Exception e) {
//            result.setStatus(MStatus.UNKNOWN);
//            result.setMessage("网络错误！请稍后查看");
//        } finally {
//            return result;
//        }
//
//    }
//
//
//    /**
//     * 根据传入json字符串查询
//     *
//     * @param jsonStr string
//     */
//    @Override
//    public void queryByJsonString(String name, String jsonStr) {
//        JSONObject json = JSONUtil.parseObj(jsonStr, false, true);
//        queryByJsonObject(name, json);
//    }
//
//    /**
//     * 使用Hutool的Json处理
//     * 最终真正实现查询的方法
//     *
//     * @param allMachineJson json对象
//     * @return 查询结果Map key:洗衣机名  value: 洗衣机状态
//     */
//    @Override
//    public void queryByJsonObject(String buildingName, JSONObject allMachineJson) {
//        class CheckWorker implements Runnable{
//            private String building;
//            private JSONObject json;
//
//            public CheckWorker(String building, JSONObject json) {
//                this.building = building;
//                this.json = json;
//            }
//
//            @Override
//            public void run() {
//                QueryResult qs = checkDependOnMachineKind(json);  // 发起查询
//                if (qs != null && !qs.isInit()) {
//                    qs.setLocation((String) json.get("location"));
//                    qs.setDate(new Date());
//                    redisUtil.hashSet(building, (String) json.get("name"),qs);
//                }
//            }
//        }
//
//
//        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) beanFactory.getBean("taskExecutor");
//        for (Map.Entry<String, Object> entry :
//                allMachineJson.entrySet()) {
//            // 先这样，后面需要先修改Checker的逻辑再来动这一块
//            executor.submit(new CheckWorker(buildingName, (JSONObject) entry.getValue()));
//        }
//
//    }
//}
