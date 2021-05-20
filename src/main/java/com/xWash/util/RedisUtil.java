package com.xWash.util;

import com.xWash.Exception.RedisException;
import com.xWash.service.Impl.UCleanChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Component
public class RedisUtil {

    @Autowired
    Jedis jedis;
    @Autowired
    RedisTemplate<String, Integer> rtInt;
    @Autowired
    RedisTemplate<String, String> rtStr;

    public void setStr_Int(String key,int value){
        // TODO logging
        ValueOperations<String, Integer> vo = rtInt.opsForValue();
        vo.set(key,value);
    }

    public void setStr_Str(String key, String value){
        ValueOperations<String, String> vo = rtStr.opsForValue();
        vo.set(key,value);
    }

    public String getStr_Str(String key){
        ValueOperations<String , String > vo = rtStr.opsForValue();
        return vo.get(key);
    }

    public Integer getStr_Int(String key){
        ValueOperations<String , Integer > vo = rtInt.opsForValue();
        return vo.get(key);
    }

    public static String decodeUTF8Str(String xStr) throws UnsupportedEncodingException {
        return URLDecoder.decode(xStr.replaceAll("\\\\x", "%"), "utf-8");
    }

}
