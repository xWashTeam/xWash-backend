package com.xWash.service;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpResponse;
import com.xWash.entity.QueryResult;

import java.net.SocketTimeoutException;

public interface IChecker {
    // TODO 使用策略模式 + DI
    public QueryResult checkByQrLink(String qrLink);
    public HttpResponse getResponse(String qrLink) throws HttpException;
}
