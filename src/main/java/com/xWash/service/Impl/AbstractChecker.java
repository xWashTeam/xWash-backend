package com.xWash.service.Impl;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONException;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.MessageEnum;
import com.xWash.model.entity.QueryResult;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

@Component
public abstract class AbstractChecker {
    @Autowired
    protected RedisUtil redisUtil;

    // key in redis
    protected String authKey = "";

    protected String getToken() {
        return redisUtil.getStr(authKey);
    }

    protected abstract HttpResponse request(Machine machine) throws IORuntimeException;

    protected abstract QueryResult extract(HttpResponse response) throws JSONException;

    public QueryResult check(Machine machine) {
        QueryResult qr = new QueryResult();
        HttpResponse response = null;
        try {
            response = request(machine);
        } catch (IORuntimeException | HttpException e) {
            qr.setStatus(MStatus.UNKNOWN);
            if (e.getCause() instanceof SocketTimeoutException) {
                qr.setMessage(MessageEnum.MESSAGE_NETWORK_TIMEOUT);
            } else {
                qr.setMessage(MessageEnum.MESSAGE_UNKNOWN);
            }
            return qr;
        }

        assert response != null;
        // auth judge
        int status = response.getStatus();
        if (status >= 400 && status < 500) {
            qr.setStatus(MStatus.UNKNOWN);
            qr.setMessage(MessageEnum.MESSAGE_UNAUTH);
            return qr;
        }

        try {
            qr = extract(response);
        } catch (JSONException e) {
            qr.setStatus(MStatus.UNKNOWN);
            qr.setMessage(MessageEnum.MESSAGE_JSON_PARSE_EXCEPTION);
        }

        return qr;
    }
}
