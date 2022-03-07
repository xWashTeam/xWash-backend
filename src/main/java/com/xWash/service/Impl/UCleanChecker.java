package com.xWash.service.Impl;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("uCleanChecker")
public class UCleanChecker extends AbstractChecker{

    private static final int timeout = 2000;
    private static final String UCLEAN_AVAILABLE = "0";
    private static final String UCLEAN_USING = "1";
    private final String url = "https://u.zhinengxiyifang.cn/api/Devices";
    private final String para1="?filter={\"where\":{\"qrCode\":\"";
    private final String para2="\",\"isRemoved\":false},\"scope\":{\"fields\":[\"virtualId\",\"scanSelfClean\",\"hasAutoLaunchDevice\",\"autoLaunchDeviceOutOfStock\",\"isSlotMachine\",\"deviceTypeId\",\"online\",\"status\",\"boxTypeId\",\"sn\"]},\"include\":[{\"relation\":\"store\",\"scope\":{\"fields\":[\"isRemoved\",\"enable\"]}}]}";

    @Override
    protected HttpResponse request(Machine machine) throws IORuntimeException {
        return HttpRequest.get(url + para1 + machine.getLink() + para2)
                .timeout(timeout)
                .execute();
    }

    @Override
    protected QueryResult extract(HttpResponse response) throws JSONException {
        String body = response.body();
        JSONObject json = JSONUtil.parseObj(body.substring(1, body.length() - 1));
        QueryResult qs = new QueryResult();
        qs.setName(json.getStr("readableName"));
        qs.setRemainTime(json.getInt("remainTime"));
        String status = json.getStr("status");
        if (status.equals(UCLEAN_AVAILABLE)) {
            qs.setStatus(MStatus.AVAILABLE);
        } else if (status.equals(UCLEAN_USING)) {
            qs.setStatus(MStatus.USING);
        } else {
            qs.setStatus(MStatus.UNKNOWN);
        }
        return qs;
    }

}

