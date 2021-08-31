package com.xWash.service.Impl;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.*;
import com.xWash.service.IChecker;
import com.xWash.entity.QueryResult;
import com.xWash.entity.MStatus;
import org.springframework.stereotype.Service;

@Service("uCleanChecker")
public class UCleanChecker implements IChecker{

    private int timeout = 2000;
    private String hostUrl = "https://u.zhinengxiyifang.cn/api/Devices";
    private String para1="?filter={\"where\":{\"qrCode\":\"";
    private String para2="\",\"isRemoved\":false},\"scope\":{\"fields\":[\"virtualId\",\"scanSelfClean\",\"hasAutoLaunchDevice\",\"autoLaunchDeviceOutOfStock\",\"isSlotMachine\",\"deviceTypeId\",\"online\",\"status\",\"boxTypeId\",\"sn\"]},\"include\":[{\"relation\":\"store\",\"scope\":{\"fields\":[\"isRemoved\",\"enable\"]}}]}";

    public QueryResult checkByQrLink(String qrLink) throws HttpException {
        return dealWithResponse(getResponse(qrLink).body());
    }

    public HttpResponse getResponse(String qrLink) {
        return HttpRequest.get(hostUrl + para1+qrLink+para2).timeout(timeout).execute();
    }

    private QueryResult dealWithResponse(String response){
        // 对返回的字符串进行处理
        QueryResult queryResult = new QueryResult();
        try {
            JSONObject jo = null;
            response = response.substring(1,response.length()-1); // 去除左右中括号
            jo = JSONUtil.parseObj(response);
            queryResult.setName((String) jo.get("readableName"));
            queryResult.setRemainTime((Integer) jo.get("remainTime"));
            String s = (String) jo.get("status");
            if(s.equals("0"))
                queryResult.setStatus(MStatus.AVAILABLE);
            else if(s.equals("1"))
                queryResult.setStatus(MStatus.USING);
            else
                queryResult.setStatus(MStatus.UNKNOWN);
        }catch (Exception e){
            queryResult.setStatus(MStatus.UNKNOWN);
            queryResult.setMessage("故障中...");
        }finally {
            return queryResult;
        }
    }

}

