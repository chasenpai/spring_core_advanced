package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    /**
     * 포인트컷(Pointcut)
     * - 어디서 부가 기능을 적용할지 말지 판단하는 필터링 로직으로
     * - 이름 그대로 포인트를 적용할 곳을 잘라서 구분하는 것
     *
     * 어드바이스(Advice)
     * - 프록시가 호출하는 부가 기능으로, 단순하게 프록시 로직이라고 생각하면 된다
     *
     * 어드바이저(Advisor)
     * - 단순하게 하나의 포인트컷과 하나의 어드바이스를 가지고 있는 것
     *
     * 역할과 책임
     * - 포인트컷은 대상 여부 필터링, 어드바이스는 부가 기능 로직 담당
     * - 둘을 합치면 어드바이저
     */
    @Test
    void advisorTest1(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //DefaultPointcutAdvisor - advisor 인터페이스의 가장 일반적인 구현체
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        /**
         * NameMatchMethodPointcut - 메서드 이름 기반 매칭
         * JdkRegexpMethodPointcut - JDK 정규 표현식 기반 매칭
         * TruePointcut - 항상 참을 반환
         * AnnotationMatchingPointcut - 애노테이션으로 매칭
         * AspectJExpressionPointcut - aspectJ 표현식 매칭 (가장 중요)
         */
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }

    }

    static class MyMethodMatcher implements MethodMatcher {

        private String matchName = "save";

        //클래스의 정적 정보를 사용하기 때문에 스프링이 내부에서 캐싱을 사용해서 성능 향상
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트컷 호출 method = {} target class = {}", method.getName(), targetClass);
            log.info("포인트컷 결과 = {}", result);
            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        //isRuntime 이 true 면 실행된다
        //매개변수가 동적이기 때문에 캐싱을 하지 않는다
        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }

    }

}
