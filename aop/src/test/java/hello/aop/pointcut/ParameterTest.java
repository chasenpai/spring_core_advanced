package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy = {}", memberService.getClass());
        memberService.hello("world");
    }

    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember(){}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {}, arg = {}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg = {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArg3(String arg) {
            log.info("[logArgs3] arg = {}", arg);
        }

        @Before("allMember() && this(obj)")
        public void thisArg(JoinPoint joinPoint, MemberService obj) {
            log.info("[this] {}, obj = {}", joinPoint.getSignature(), obj.getClass()); //프록시 객체 전달
        }

        @Before("allMember() && target(obj)")
        public void targetArg(JoinPoint joinPoint, MemberService obj) {
            log.info("[target] {}, obj = {}", joinPoint.getSignature(), obj.getClass()); //실제 대상 객체 전달
        }

        @Before("allMember() && @target(annotation)")
        public void atTargetArg(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[atTarget] {}, annotation = {}", joinPoint.getSignature(), annotation); //타입의 애노테이션 전달
        }

        @Before("allMember() && @within(annotation)")
        public void atWithinArg(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[atWithin] {}, annotation = {}", joinPoint.getSignature(), annotation); //타입의 애노테이션 전달
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotationArg(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[atAnnotation] {}, value = {}", joinPoint.getSignature(), annotation.value()); //메서드의 애노테이션 전달
        }

    }

}
