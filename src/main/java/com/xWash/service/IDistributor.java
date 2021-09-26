package com.xWash.service;


import cn.hutool.json.JSONObject;
import com.xWash.entity.QueryResult;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public interface IDistributor {
    void queryByJsonString(String name, String jsonStr);
    void queryByJsonObject(String name, JSONObject json);
}
