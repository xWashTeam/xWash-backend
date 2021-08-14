package com.xWash.service;

import com.xWash.entity.QueryResult;

public interface IChecker {
    // TODO 使用策略模式 + DI
    public QueryResult checkByQrLink(String qrLink);
    public String getResponse(String qrLink);
}
