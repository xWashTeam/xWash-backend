package com.xWash.aspect;

import com.xWash.model.dao.Record;
import com.xWash.model.dao.User;
import com.xWash.util.VisitorUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component("cookiesAspect")
@Aspect
public class CookiesAspect {
    @Autowired
    VisitorUtil visitorUtil;

    @Pointcut("execution(* com.xWash.controller.CheckController.getMachineStatus(..))")
    private void pt4checkController() {
    }

    @Before("pt4checkController() && args(building,autoUpdate,userCookieStr,*,response,..)")
    private void beforeCheck(String building, String autoUpdate, String userCookieStr
            , HttpServletResponse response) {
        // new user
        if (userCookieStr == null || visitorUtil.getUserByCookie(userCookieStr) == null) {
            Cookie cookie = visitorUtil.generateRandUserCookie();
            userCookieStr = cookie.getValue();
            response.addCookie(cookie);
            User user = new User();
            user.setCookie(userCookieStr);
            visitorUtil.addUser(user);
        }

        // new record
        Record record = new Record();
        record.setBuilding(building);
        record.setCookie(userCookieStr);
        record.setMode(autoUpdate != null ? "autoUpdate" : "normal");
        visitorUtil.addRecord(record);
    }
}
