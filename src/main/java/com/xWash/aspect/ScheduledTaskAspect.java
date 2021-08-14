package com.xWash.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component("scheduledTaskAspect")
public class ScheduledTaskAspect {
    Logger logger = LogManager.getLogger("checkerLog");

    @Pointcut("execution(* com.xWash.tasks.APIChecker.checkFromAPI(..))")
    private void pt4CheckFromAPI() {
    }

    @AfterThrowing(pointcut = "pt4CheckFromAPI()",throwing = "ex")
    public void handleCheckException(Exception ex){
        logger.error(ex.getMessage());
        logger.error(ex.getStackTrace());
    }


}
