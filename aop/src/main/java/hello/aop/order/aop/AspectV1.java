package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect //컴포넌트 스캔이 되는건 아니기 때문에 빈으로 등록해야 함
public class AspectV1 {

    //스프링 AOP 는 AspectJ 의 문법을 차용하여 프록시 방식 AOP 를 제공하는 것이지 AspectJ 를 직접 사용하는 것이 아님

    //@Around 의 값은 포인트컷, doLog 메서드는 어드바이스 -> 어드바이저
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니쳐
        return joinPoint.proceed();
   }

}
