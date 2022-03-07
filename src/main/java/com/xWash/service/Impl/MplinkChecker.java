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

@Service("mplinkChecker")
public class MplinkChecker extends AbstractChecker {
    private final static int timeout = 10000;
    private final static String url = "http://view.mplink.cn/ajax/GetDeviceStatus.ashx?deviceID=";


    @Override
    protected HttpResponse request(Machine machine) throws IORuntimeException {
        String link = machine.getLink();
        String id = link.substring(link.indexOf('=') + 1);
        return HttpRequest
                .get(url + id)
                .timeout(timeout)
                .execute();
    }

    @Override
    protected QueryResult extract(HttpResponse response) throws JSONException {
        QueryResult qr = new QueryResult();
        String text = response.body();
        JSONObject json = JSONUtil.parseObj(text.substring(1, text.length() - 1));
        String status = (String) json.get("Value");
        switch (status) {
            case "1":
                qr.setStatus(MStatus.AVAILABLE);
                break;
            case "0":
                qr.setStatus(MStatus.USING);
                qr.setMessage("设备正在运行");
                break;
            case "4":
                qr.setStatus(MStatus.UNKNOWN);
                qr.setMessage("套餐已缺货,请选择其他套餐");
                break;
            case "99":
                qr.setStatus(MStatus.UNKNOWN);
                qr.setMessage("设备尚未准备就绪，请稍候重试");
                break;
            case "100":
                qr.setStatus(MStatus.USING);
                qr.setMessage("设备正在运行，请勿重复支付");
                break;
            default:
                qr.setStatus(MStatus.UNKNOWN);
                qr.setMessage("未知错误!");
        }
        return qr;
    }

}
