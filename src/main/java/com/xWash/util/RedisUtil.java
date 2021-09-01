package com.xWash.util;

import com.xWash.Exception.RedisException;
import com.xWash.entity.QueryResult;
import com.xWash.service.Impl.UCleanChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Component
public class RedisUtil {

    @Autowired
    Jedis jedis;
    @Autowired
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    public boolean hashExist(String key){
        return redisTemplate.hasKey(key);
    }

    public Map hashGetAll(String key){
        return  redisTemplate.opsForHash().entries(key);
    }

    public String hashGet(String key, String field){
        return (String) redisTemplate.opsForHash().get(key,field);
    }

    public void hashSet(String key, String field, Object value){
        redisTemplate.opsForHash().put(key, field, value);
    }

    public void hashRemove(String key, String field){
        redisTemplate.opsForHash().delete(key,field);
    }

    public void setStr_Str(String key, String value){
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set(key,value);
    }

    public String getStr_Str(String key){
        ValueOperations<String , String > vo = redisTemplate.opsForValue();
        return vo.get(key);
    }



    public static String decodeUTF8Str(String xStr) throws UnsupportedEncodingException {
        return URLDecoder.decode(xStr.replaceAll("\\\\x", "%"), "utf-8");
    }

}
