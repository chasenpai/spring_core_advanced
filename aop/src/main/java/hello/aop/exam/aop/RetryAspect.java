package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retry)") //어드바이스에 애노테이션 파라미터 전달
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("[retry] {} retry = {}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for(int retryCnt = 1; retryCnt <= maxRetry; retryCnt++){ //에외 발생 시 retry.value() 만큼 재시도
            try{
                log.info("[retry] try count = {}/{}", retryCnt, maxRetry);
                return joinPoint.proceed();
            }catch (Exception e){
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }

}
