package controller;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by eustali on 16.02.2017.
 */
@Aspect
@Component
public class LoggerController {
    Logger log = Logger.getRootLogger();

    public LoggerController () {}

/*    @AfterReturning ("execution(* controller.*..*(..))")
    public void logMethodAccessAfter(JoinPoint joinPoint) {
        log.info("***** Completed: " + joinPoint.getSignature().getName() + " *****");
    }*/

    @Before ("execution(* controller.*..*(..))")
    public void logMethodAccessBefore(JoinPoint joinPoint) {
        log.info("[" + joinPoint.getTarget().getClass() + "." +
                 joinPoint.getSignature().getName() + "(" + Arrays.toString(joinPoint.getArgs()) + ")]");

    }
}
