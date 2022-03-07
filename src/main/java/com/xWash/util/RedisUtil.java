package com.xWash.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Map;

@Component
public class RedisUtil {
    @Autowired
    JedisPool jedisPool;

    public boolean exist(String key) {
        Jedis jedis = jedisPool.getResource();
        boolean exist = jedis.exists(key);
        jedis.close();
        return exist;
    }

    public Map<String, String> hashGetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = jedis.hgetAll(key);
        jedis.close();
        return map;
    }

    public String hashGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String value =  jedis.hget(key, field);
        jedis.close();
        return value;
    }

    public void hashSet(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key, field, value);
        jedis.close();
    }

    public void setStr(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    public String getStr(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
}
