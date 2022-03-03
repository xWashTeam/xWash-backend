package com.xWash.service.Impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("uCleanAppChecker")
public class UCleanAppChecker extends AbstractChecker {
    private int TIMEOUT = 2000;
    private String URL = "https://phoenix.ujing.online:443/api/v1/devices/scanWasherCode";

    {
        authKey = "ucleanapp";
    }


    @Override
    protected HttpResponse request(Machine machine) throws IOException {
        JSONObject json = new JSONObject();
        json.putOnce("qrCode", machine.getLink());
        return HttpRequest
                .post(URL)
                .timeout(TIMEOUT)
                .header("Authorization", getToken())
                .body(String.valueOf(json))
                .execute();
    }

    @Override
    protected QueryResult extract(HttpResponse response) throws IOException {
        QueryResult qr = new QueryResult();
        JSONObject json = JSONUtil.parseObj(response.body());
        Boolean available = (Boolean) json.getByPath("data.result.createOrderEnabled");
        if (available) {
            qr.setStatus(MStatus.AVAILABLE);
            return qr;
        }
        String reason = (String) json.getByPath("data.result.reason");
        qr.setMessage(reason);
        if ((int) json.getByPath("data.result.status") == 1) {
            qr.setStatus(MStatus.USING);
        } else {
            qr.setStatus(MStatus.UNKNOWN);
        }
        return qr;
    }

    @Override
    public QueryResult check(Machine machine) {
        try {
            HttpResponse response = request(machine);
            return extract(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
