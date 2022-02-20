//package com.xWash.aspect;
//
//import com.xWash.mapper.UserDao;
//import com.xWash.model.dao.Record;
//import com.xWash.util.CookieUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//
//@Component("cookiesAspect")
//@Aspect
//public class CookiesAspect {
//    @Autowired
//    @Qualifier("userMapper")
//    Object userMapper;
//
//    Logger logger = LogManager.getLogger("sqlLog");
//
//    @Pointcut("execution(* com.xWash.controller.CheckController.getMachineStatus(..))")
//    private void pt4checkController() {
//    }
//
//    @Before("pt4checkController() && args(building,autoUpdate,userCookieStr,*,response,..)")
//    private void beforeCheck(String building, String autoUpdate, String userCookieStr
//            , HttpServletResponse response) {
//        Cookie userCookie;
//        try{
//            UserDao userDao = (UserDao) userMapper;
//            if (userCookieStr == null || userCookieStr.equals("") || userDao.getUserByCookie(userCookieStr) == null) {
//                // 无cookie ， 需要新建cookie
//                do {
//                    userCookieStr = CookieUtil.generateRandUserCookie();
//                } while (userDao.getUserByCookie(userCookieStr) != null);
//                userCookie = new Cookie("user", userCookieStr);
//                userCookie.setMaxAge(60 * 60 * 24 * 365 * 4);
//                userCookie.setPath("/");
//                response.addCookie(userCookie);
//                userDao.addUser(userCookieStr);
//            }
//            // 记录本次访问
//            Record record = new Record();
//            record.setBuilding(building);
//            record.setCookie(userCookieStr);
//            if (autoUpdate != null)
//                record.setMode("autoUpdate");
//            else
//                logger.info(userCookieStr + " comes");
//            userDao.addRecord(record);
//        }catch(Exception e){
//            logger.warn(e.getMessage());
//        }
//    }
//}
