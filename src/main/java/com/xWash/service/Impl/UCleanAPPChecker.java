package com.xWash.service.Impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.MStatus;
import com.xWash.entity.QueryResult;
import com.xWash.service.IChecker;
import org.springframework.stereotype.Service;

@Service("uCleanAppChecker")
public class UCleanAPPChecker implements IChecker {
    private int timeout = 2000;
    private String host = "https://phoenix.ujing.online:443/api/v1/devices/scanWasherCode";
    private String Authorization = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTA2NjM4NDIsIm5hbWUiOiIxMzAyNTE2MDAzNiIsImFwcFVzZXJJZCI6MTI2OTcxNzksImlhdCI6MTYyMDk4NjE3MywiZXhwIjoxNjI5MDIxMzczfQ.TJvITEHADsLSBCvzyxtdhYOLrsIx4mTVDsB_ewS0-B8";

    @Override
    public String getResponse(String qrLink){
        JSONObject jo = new JSONObject();
        jo.putOnce("qrCode",qrLink);
        return HttpRequest.post(host).timeout(timeout).header("Authorization",Authorization).body(String.valueOf(jo))
                .execute().body();
    }

    @Override
    public QueryResult checkByQrLink(String qrLink) throws HttpException {
        JSONObject resJson = JSONUtil.parseObj(getResponse(qrLink));
        return dealWithResponse(resJson);
    }

    public QueryResult dealWithResponse(JSONObject resJson){
        QueryResult qr = new QueryResult();
        Boolean available = (Boolean) resJson.getByPath("data.result.createOrderEnabled");
        if (available){
            qr.setStatus(MStatus.AVAILABLE);
        }else{
            String reason = (String) resJson.getByPath("data.result.reason");
            qr.setMessage(reason);
            if((int) resJson.getByPath("data.result.status") == 1 ){
                qr.setStatus(MStatus.USING);
            }else{
                qr.setStatus(MStatus.UNKNOWN);
            }
        }
        qr.setRemainTime(-1);
        return qr;
    }
}
