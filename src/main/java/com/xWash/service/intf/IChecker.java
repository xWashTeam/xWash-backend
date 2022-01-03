package com.xWash.service.intf;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpResponse;
import com.xWash.model.entity.QueryResult;

public interface IChecker {
    // TODO 使用策略模式 + DI
    public QueryResult checkByQrLink(String qrLink);
    public HttpResponse getResponse(String qrLink) throws HttpException;
}
