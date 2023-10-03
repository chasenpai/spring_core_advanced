package hello.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TraceAspect {

    @Before("@annotation(hello.aop.exam.annotation.Trace)")
    public void doTrace(JoinPoint joinPoint) {

        log.info("[trace] {}", joinPoint.getSignature());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("args = {}", arg);
        }
    }

}
