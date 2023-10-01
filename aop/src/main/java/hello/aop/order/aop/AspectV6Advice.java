package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    /**
     * @Around
     * - 메서드 호출 전후로 실행
     * - 가장 강력한 어드바이스로 조인 포인트 실행 여부 선택, 전달값 변환, 반환값 변환, 예외 변환 등 수행
     * - 첫번째 파라미터는 ProceedingJoinPoint 를 사용해야 함
     * - proceed() 를 호출하여 대상을 실행하고 여러번 실행할 수 있다
     */
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        /**
         * JoinPoint 주요 기능
         * getArgs() - 메서드 인수 반환
         * getThis() - 프록시 객체 반환
         * getTarget() - 대상 객체 반환
         * getSignature() - 조언되는 메서드에 대한 설명 반환
         * toString() - 조언되는 방법에 대한 유용한 설명 출력
         *
         * ProceedingJoinPoint
         * proceed() - 다음 어드바이스나 타겟을 호출
         */
        try{
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        }catch (Exception e){
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        }finally {
            //@After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    /**
     * @Before
     * - 조인 포인트 실행 이전에 실행
     * - 메서드 종료 시 자동으로 다음 대상 호출
     */
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    /**
     * @AfterReturning
     * - 조인 포인트가 정상 완료후 실행
     * - returning 속성에 사용된 이름은 어드바이스의 메서드 매개변수와 일치해야 한다
     * - 지정된 타입의 값을 반환하는 메서드만 대상으로 실행(부모 타입 지정시 모든 자식 타입은 인정)
     * - 반환되는 객체를 변경할 수 없지만 조작할 순 있다
     */
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[returning] {}, return = {}", joinPoint.getSignature(), result);
    }

    /**
     * @AfterThrowing
     * - 메서드가 예외를 던지는 경우 실행
     * - throwing 속성에 사용된 이름은 어드바이스의 메서드 매개변수와 일치해야 한다
     * - throwing 절에 지정된 타입과 맞는 예외를 대상으로 실행(부모 타입 지정시 모든 자식 타입은 인정)
     */
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[throwing] {}, message = {}", joinPoint.getSignature(), ex);
    }

    /**
     * @After
     * - 조인 포인트가 정상 또는 예외 관계 없이 실행(finally)
     * - 일반적으로 리소스 해제 시 사용
     */
    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after] {}", joinPoint.getSignature());
    }

}
