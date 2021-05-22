package com.xWash.aspect;

import com.xWash.dao.UserDao;
import com.xWash.entity.Record;
import com.xWash.util.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component("cookiesAspect")
@Aspect
public class CookiesAspect {
    @Pointcut("execution(* com.xWash.admin.CheckController.getMachineStatus(..))")
    private void pt4checkController() {
    }

    @Before("pt4checkController() && args(building,userCookieStr,*,response,..)")
    private void beforeCheck(String building, String userCookieStr, HttpServletResponse response) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) ac.getBean("userMapper");
        Cookie userCookie;
        if (userCookieStr == null || userCookieStr.equals("") || userDao.getUserByCookie(userCookieStr) == null) {
            // 无cookie ， 需要新建cookie
            do {
                userCookieStr = CookieUtil.generateRandUserCookie();
            } while (userDao.getUserByCookie(userCookieStr) != null);
            userCookie = new Cookie("user", userCookieStr);
            userCookie.setMaxAge(60 * 60 * 24 * 365 * 4);
            response.addCookie(userCookie);
            userDao.addUser(userCookieStr);
        }
        // 记录本次访问
        Record record = new Record();
        record.setBuilding(building);
        record.setCookie(userCookieStr);
        record.setDate(new Date());
        userDao.addRecord(record);
    }
}
