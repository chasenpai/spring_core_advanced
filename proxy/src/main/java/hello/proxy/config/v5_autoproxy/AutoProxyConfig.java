package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    /**
     * 자동 프록시 생성기
     * - AnnotationAwareAspectJAutoProxyCreator 라는 빈 후처리기가 스프링 빈에 자동으로 등록된다
     * - 해당 빈 후처리기가 Advisor 들을 찾아서 프록시가 필요한 곳에 자동으로 프록시를 적용해준다
     * - 프록시 적용 대상은 Pointcut 으로 판단하고, 부가 기능은 Advice 로 적용한다
     *
     * 프록시 적용 여부 판단
     * - 자동 프록시 생성기가 포인트컷으로 해당 빈이 프록시를 생성할 필요가 있는지 판단한다
     * - 클래스 + 메서드 조건을 모두 비교하고 포인트컷 조건에 하나라도 맞는것이 있으면 프록시를 생성한다
     *
     * 어드바이스 적용 여부 판단
     * - 프록시가 호출되었을 때 어드바이스를 적용할지 말지 포인트컷을 보고 판단한다
     * - 예) OrderControllerV1 은 이미 프록시가 적용되어 있다
     * - request()는 포인트컷 조건에 만족하므로 프록시는 어드바이스를 먼저 호출하고 타겟을 호출한다
     * - noLog()는 포인트컷 조건에 만족하지 않으므로 어드바이스를 호출하지 않고 바로 타겟을 호출한다
     *
     * 하나의 프록시에 여러 어드바이저
     * - 어드바이저가 여러 개여도 하나의 프록시만 생성된다
     * - 프록시가 내부에 어드바이저들을 포함할 수 있기 때문
     */
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor2(LogTrace logTrace){

        //AspectJ 포인트컷 표현식 사용 - 매우 정밀한 포인트컷
        // * - 모든 반환 타입
        // hello.proxy.app.. - 해당 패키지와 그 하위 패키지
        // *(..) - * 모든 메서드 이름, (..) 파라미터는 상관 없음
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
