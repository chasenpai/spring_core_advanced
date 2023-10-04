package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    /**
     * 프록시와 내부 호출 문제
     * - AOP 를 적용하면 스프링은 대상 객체 대신에 프록시를 스프링 빈으로 등록한다
     * - 따라서 스프링은 의존관계 주입 시 항상 프록시 객체를 주입하기 때문에 일반적으로
     * - 객체를 직접 호출하는 문제는 발생하지 않는다  하지만 대상 객체 내부에서 메서드 호출이 발생하면
     * - 프록시를 거치지 않기 떄문에 AOP 가 적용되지 않는다
     */
    public void external() {
        log.info("call external");
        internal(); //내부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }

}
