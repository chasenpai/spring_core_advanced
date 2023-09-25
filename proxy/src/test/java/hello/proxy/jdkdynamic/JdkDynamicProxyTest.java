package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    /**
     * Jdk 동적 프록시
     * - jdk 동적 프록시 기술을 사용하면 적용 대상 만큼 프록시 객체를 만들지 않아도 된다
     * - 같은 부가 기능 로직을 한번만 개발해서 공통으로 적용할 수 있다
     * - 프록시 클래스를 여러 개 만들어야 하는 문제를 해결하고 부가 기능 로직도
     * - 하나의 클래스에 모아서 단일 책임 원칙(SRP)를 지킬 수 있다
     */
    @Test
    void dynamicA(){

        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target); //동적 프록시에 적용할 핸들러

        //넘겨준 인터페이스를 기반으로 동적 프록시를 생성
        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        //1. 동적 프록시의 call()이 실행되고 구현체인 TimeInvocationHandler.invoke() 가 호출된다
        //2. TimeInvocationHandler 가 내부 로직을 실행하고 target 인 실제 객체 AImpl 인스턴스의 call() 이 실행된다
        //3. AImpl 의 call() 의 실행이 끝나면 TimeInvocationHandler 로 응답이 돌아온다
        proxy.call();
        log.info("targetClass = {}", target.getClass());//hello.proxy.jdkdynamic.code.BImpl
        log.info("proxyClass = {}", proxy.getClass()); //jdk.proxy2.$Proxy10
    }

    @Test
    void dynamicB(){

        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxy.call();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }

}
