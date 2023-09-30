package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    /**
     * 프록시 팩토리
     * - 스프링은 인터페이스가 있는 경우엔 JDK 동적 프록시, 그렇지 않으면 CGLIB 를
     * - 공통으로 적용하기 위한 프록시 팩토리라는 기능을 제공한다
     */
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice()); //프록시가 사용할 부가 기능 로직을 설정 - 어드바이스
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue(); //프록시 팩토리를 통해 생성 여부
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue(); //JDK 동적 프록시 사용 여부
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse(); //CGLIB 사용 여부
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy(){

        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB 를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //클래스 기반으로 프록시 사용 (CGLIB)
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}
