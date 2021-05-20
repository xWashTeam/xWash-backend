package com.xWash.dao;

import com.xWash.entity.Feedback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:spring-mybatis.xml"})
public class MybatisTest {

    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        FeedbackDao feedbackMapper = (FeedbackDao)context.getBean("feedbackMapper");
        Feedback fb = new Feedback();
        fb.setName("IDEA");
        fb.setText("hello there, this i          s from IDEA");
        feedbackMapper.saveFeedback(fb);
    }
}
