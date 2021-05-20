package com.xWash.service;


import cn.hutool.json.JSONObject;
import com.xWash.entity.QueryResult;

import java.io.File;
import java.util.HashMap;

public interface IDistributor {
    HashMap<String, QueryResult> queryByPath(String path);
    HashMap<String, QueryResult> queryByFile(File file);
    HashMap<String, QueryResult> queryByJsonString(String jsonStr);
    HashMap<String, QueryResult> queryByJsonObject(JSONObject json);
}
