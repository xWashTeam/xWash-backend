package com.xWash.service.Impl;

import cn.hutool.http.HttpResponse;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.QueryResult;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public abstract class  AbstractChecker {
    @Autowired
    protected RedisUtil redisUtil;

    // key in redis
    protected String authKey = "";
    protected String getToken() {
        return redisUtil.getStr_Str(authKey);
    }

    protected abstract HttpResponse request(Machine machine) throws IOException;
    protected abstract QueryResult extract(HttpResponse response) throws IOException;
    public abstract QueryResult check(Machine machine);
}
