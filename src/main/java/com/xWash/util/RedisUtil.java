package com.xWash.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class RedisUtil {
    @Autowired
    JedisPool jedisPool;

    public boolean exist(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.exists(key);
    }

    public Map<String, String> hashGetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.hgetAll(key);
    }

    public String hashGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        return jedis.hget(key, field);
    }

    public void hashSet(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key, field, value);
    }

    public void setStr(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
    }

    public String getStr(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.get(key);
    }
}
