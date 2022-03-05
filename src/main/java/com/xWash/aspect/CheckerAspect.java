package com.xWash.aspect;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpResponse;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MessageEnum;
import com.xWash.model.entity.QueryResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component("checkerAspect")
public class CheckerAspect {
    final Logger logger = LogManager.getLogger("checkerLog");

    @Pointcut("execution(* com.xWash.service.Impl.AbstractChecker.check(..))")
    public void checkPoint() {
    }

    /**
     * Around weave for request() to catch exception and specific response
     *
     * @param jp
     * @return
     */
    @Around(value = "checkPoint()")
    public Object aroundRequest(ProceedingJoinPoint jp) {
        Machine machine = (Machine) (jp.getArgs()[0]);
        QueryResult qr = new QueryResult();
        try {
            qr = (QueryResult) jp.proceed();
            if (!qr.isNormal()) {
                logger.warn(machine.getName() + ": " + qr.getMessage());
            }
        } catch (Exception e) {
            logger.error(machine.getName() + ": " + e.getCause());
            logger.error(e.getMessage());
        } finally {
            return qr;
        }
    }
}
