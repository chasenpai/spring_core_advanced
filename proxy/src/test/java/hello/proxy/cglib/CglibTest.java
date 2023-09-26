package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    /**
     * CGLIB
     * - 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리
     * - 인터페이스가 없어도 구체 클래스만 가지고 동적 프록시를 만들어낼 수 있다
     * - 스프링에 기본적으로 포함되어있다
     * - 하지만 클래스 기반 프록시는 상속을 사용하기 때문에 제약들이 따른다
     */
    @Test
    void cglib(){

        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer(); //CGLIB 는 Enhancer 를 사용하여 프록시 생성
        enhancer.setSuperclass(ConcreteService.class); //상속 받을 구체 클래스 지정
        enhancer.setCallback(new TimeMethodInterceptor(target)); //프록시에 지정할 실행 로직 할당
        ConcreteService proxy = (ConcreteService) enhancer.create(); //프록시 생성
        log.info("targetClass = {}", target.getClass()); //hello.proxy.common.service.ConcreteService
        log.info("proxyClass = {}", proxy.getClass()); //hello.proxy.common.service.ConcreteService$$EnhancerByCGLIB$$25d6b0e3

        proxy.call();
    }

}
