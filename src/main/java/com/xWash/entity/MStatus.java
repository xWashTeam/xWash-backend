package com.xWash.entity;

public enum MStatus {
    USING,      // 被占用
    AVAILABLE,  // 空闲可用
    UNKNOWN;    // 未知状态, 大部分时间处于被占用状态
}
