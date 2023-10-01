package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    //포인트컷 분리
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //메서드명 + 파라미터 -> 포인트컷 시그니쳐

    @Around("allOrder()") //포인트컷 시그니쳐 사용
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
   }

}
