package com.xWash.aspect;

import com.xWash.entity.QueryResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component("checkerAspect")
public class CheckerAspect {
    final Logger logger = LogManager.getLogger("checkerLog");

    @Pointcut("execution(* com.xWash.service.IChecker.checkByQrLink(..))")
    public void netRequest() { }

    @Around(value = "netRequest()")
    public Object logAround(ProceedingJoinPoint jp){
        Object result=null;
        String qrLink = (String) (jp.getArgs()[0]);
        try {
            result = jp.proceed();
            QueryResult qs = (QueryResult) result;
            synchronized (logger){
                logger.info("qrLink -> " + qrLink + ", QueryResult -> " + result);
            }
        } catch (Throwable throwable) {
            synchronized (logger){
                logger.warn("qrLink -> "+ qrLink + ", msg -> " + throwable.getMessage());
            }
        }finally {
            return result;
        }

    }
}

