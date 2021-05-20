package com.xWash.tasks;

import com.xWash.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:spring-redis.xml"})
public class redisTest {

    @Autowired
    RedisUtil r;

    @Test
    public void testRedis(){
//        r.setStr_Str("test","{\\\"TEST\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"USING\\\",\\\"remainTime\\\":35,\\\"message\\\":\\\"\\\"},\\\"d19_W_1.5\\\":{\\\"name\\\":\\\"SD59770583\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":0,\\\"message\\\":\\\"\\\"},\\\"d19_E_2.5\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"UNKNOWN\\\",\\\"remainTime\\\":45,\\\"message\\\":\\\"\\\"},\\\"d19_W_2.5\\\":{\\\"name\\\":\\\"MNBGFG0483\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":0,\\\"message\\\":\\\"\\\"},\\\"d19_E_3.5\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"USING\\\",\\\"remainTime\\\":21,\\\"message\\\":\\\"\\\"},\\\"d19_W_3.5\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":35,\\\"message\\\":\\\"\\\"},\\\"d19_W_4.5\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":35,\\\"message\\\":\\\"\\\"},\\\"d19_E_4.5\\\":{\\\"name\\\":\\\"MNBGDH0867\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":0,\\\"message\\\":\\\"\\\"},\\\"d19_W_5.5\\\":{\\\"name\\\":\\\"SD59770424\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":0,\\\"message\\\":\\\"\\\"},\\\"d19_E_5.5\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":35,\\\"message\\\":\\\"\\\"},\\\"d19_W_6.5\\\":{\\\"name\\\":\\\"MNBGFG0895\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":0,\\\"message\\\":\\\"\\\"},\\\"d19_E_6.5\\\":{\\\"name\\\":\\\"\\xe6\\xa0\\xa1\\xe5\\x9b\\xad\\xe6\\x9c\\xbaMB65-GF03W\\\",\\\"status\\\":\\\"AVAILABLE\\\",\\\"remainTime\\\":35,\\\"message\\\":\\\"\\\"}}");
//        r.setStr_Str("test",r.getStr_Str("d19"));
        r.setStr_Str("test","{\"TEST!\":{\"name\":\"测试机1\",\"status\":\"USING\",\"remainTime\":999,\"message\":\"\"},\"d19_W_1.5\":{\"name\":\"SD59770583\",\"status\":\"AVAILABLE\",\"remainTime\":0,\"message\":\"\"},\"d19_E_2.5\":{\"name\":\"校园机MB65-GF03W\",\"status\":\"UNKNOWN\",\"remainTime\":45,\"message\":\"\"},\"d19_W_2.5\":{\"name\":\"MNBGFG0483\",\"status\":\"AVAILABLE\",\"remainTime\":0,\"message\":\"\"},\"d19_E_3.5\":{\"name\":\"校园机MB65-GF03W\",\"status\":\"AVAILABLE\",\"remainTime\":35,\"message\":\"\"},\"d19_W_3.5\":{\"name\":\"校园机MB65-GF03W\",\"status\":\"AVAILABLE\",\"remainTime\":35,\"message\":\"\"},\"d19_W_4.5\":{\"name\":\"校园机MB65-GF03W\",\"status\":\"AVAILABLE\",\"remainTime\":35,\"message\":\"\"},\"d19_E_4.5\":{\"name\":\"MNBGDH0867\",\"status\":\"AVAILABLE\",\"remainTime\":0,\"message\":\"\"},\"d19_W_5.5\":{\"name\":\"SD59770424\",\"status\":\"AVAILABLE\",\"remainTime\":0,\"message\":\"\"},\"d19_E_5.5\":{\"name\":\"校园机MB65-GF03W\",\"status\":\"AVAILABLE\",\"remainTime\":35,\"message\":\"\"}}");
        System.out.println(r.getStr_Str("test"));
    }
}
