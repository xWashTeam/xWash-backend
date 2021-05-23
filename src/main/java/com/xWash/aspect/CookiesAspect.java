package com.xWash.aspect;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xWash.dao.UserDao;
import com.xWash.entity.Record;
import com.xWash.util.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component("cookiesAspect")
@Aspect
public class CookiesAspect {
    @Autowired
    @Qualifier("userMapper")
    Object userMapper;

    @Pointcut("execution(* com.xWash.admin.CheckController.getMachineStatus(..))")
    private void pt4checkController() {
    }

    @Before("pt4checkController() && args(building,autoUpdate,userCookieStr,*,response,..)")
    private void beforeCheck(String building, String autoUpdate, String userCookieStr
            , HttpServletResponse response) {

        System.out.println(userMapper);
        UserDao userDao = (UserDao) userMapper;
        Cookie userCookie;
        if (userCookieStr == null || userCookieStr.equals("") || userDao.getUserByCookie(userCookieStr) == null) {
            // 无cookie ， 需要新建cookie
            do {
                userCookieStr = CookieUtil.generateRandUserCookie();
            } while (userDao.getUserByCookie(userCookieStr) != null);
            userCookie = new Cookie("user", userCookieStr);
            userCookie.setMaxAge(60 * 60 * 24 * 365 * 4);
            userCookie.setPath("/");
            response.addCookie(userCookie);
            userDao.addUser(userCookieStr);
        }
        // 记录本次访问
        Record record = new Record();
        record.setBuilding(building);
        record.setCookie(userCookieStr);
        if (autoUpdate != null)
            record.setMode("autoUpdate");
        userDao.addRecord(record);


    }
}
