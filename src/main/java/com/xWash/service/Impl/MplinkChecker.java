package com.xWash.service.Impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import com.xWash.service.intf.IChecker;
import org.springframework.stereotype.Service;

@Service("mplinkChecker")
public class MplinkChecker implements IChecker {
    private final static int timeout = 10000;
    private final static String url = "http://view.mplink.cn/ajax/GetDeviceStatus.ashx?deviceID=";

    @Override
    public QueryResult checkByQrLink(String qrLink) {
        QueryResult qs = new QueryResult();
        HttpResponse response = null;
        try {
            response = getResponse(qrLink);
            if (response == null || response.getStatus() != 200) {
                // TODO log
                qs.setMessage("出错了，请联系管理员");
                return qs;
            }
            JSONObject jo = JSONUtil.parseObj(response.body().substring(1, response.body().length() - 1));
            String status = (String) jo.get("Value");
            switch (status) {
                case "1":
                    qs.setStatus(MStatus.AVAILABLE);
                    break;
                case "0":
                    qs.setStatus(MStatus.USING);
                    qs.setMessage("设备正在运行");
                    break;
                case "4":
                    qs.setStatus(MStatus.UNKNOWN);
                    qs.setMessage("套餐已缺货,请选择其他套餐");
                    break;
                case "99":
                    qs.setStatus(MStatus.UNKNOWN);
                    qs.setMessage("设备尚未准备就绪，请稍候重试");
                    break;
                case "100":
                    qs.setStatus(MStatus.USING);
                    qs.setMessage("设备正在运行，请勿重复支付");
                    break;
            }
        } catch (HttpException e) {

            return qs;
        } catch (Exception e) {
            // TODO log
        } finally {
            return qs;
        }

    }


    @Override
    public HttpResponse getResponse(String qrLink) {
        String deviceId = qrLink.substring(45);    // 短期内api不变，这样最方便
        return HttpRequest.get(url + deviceId)
                .timeout(timeout)
                .execute();
    }
}
