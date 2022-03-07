package com.xWash.aspect;

import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component("checkerAspect")
public class CheckerAspect {
    final Logger logger = LogManager.getLogger("checker");

    @Pointcut("execution(* com.xWash.service.Impl.AbstractChecker.check(..))")
    public void commonCheckerPoint() {
    }

    @Pointcut("execution(* com.xWash.service.Impl.WashpayerChecker.check(..))")
    public void washpayerCheckerPoint() {
    }

    /**
     * Around weave for request() to catch exception and specific response
     *
     * @param jp
     * @return
     */
    @Around(value = "commonCheckerPoint() && washpayerCheckerPoint()")
    public Object aroundRequest(ProceedingJoinPoint jp) {
        Machine machine = (Machine) (jp.getArgs()[0]);
        QueryResult qr = new QueryResult();
        try {
            qr = (QueryResult) jp.proceed();
            if (!qr.isNormal()) {
                logger.warn(machine.getName() + ": " + qr.getMessage());
            }
        } catch (Exception e) {
            qr.setStatus(MStatus.UNKNOWN);
            qr.setMessage(String.valueOf(e.getMessage()));
            logger.error(machine.getName() + " -> error: {}", e.getMessage(), e);
        } finally {
            return qr;
        }
    }
}
