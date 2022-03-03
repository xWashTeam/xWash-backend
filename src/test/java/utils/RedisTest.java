package utils;

import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisTest {
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void testParseArray() {
        redisUtil.setStr_Str("ucleanapp", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTA2NjM4NDIsIm5hbWUiOiIxMzAyNTE2MDAzNiIsImFwcFVzZXJJZCI6Im9neVJUMXU4bkxRWGMwOHJia05kTjlqOUlMOUkiLCJpYXQiOjE2NDYyOTk1ODUsImV4cCI6MTY1NDMzNDc4NX0.eh539ieGFgtEeJvB364bkaabWV81rgGSHDsFqmFswRc");
    }
}
