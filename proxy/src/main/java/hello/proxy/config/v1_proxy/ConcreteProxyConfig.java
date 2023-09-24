package hello.proxy.config.v1_proxy;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import hello.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import hello.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    /**
     * 인터페이스 기반 프록시 vs 클래스 기반 프록시
     * - 인퍼페이스 기반은 인터페이스만 같으면 모든 곳에 적용할 수 있지만 클래스 기반은 해당 클래스에만 적용 가능
     * - 클래스 기반 프록시는 상속을 사용하기 때문에 그에 따른 제약들이 생긴다
     * - 예)부모 클래스 생성자 반드시 호출, 클래스에 final 이 붙으면 상속 불가능, 메서드에 final 붙으면 오버라이딩 불가능
     * - 인터페이스 기반 프록시는 상속이라는 제약에서 자유롭고, 프로그래밍 관점에서도 역할과 구현을 나누기 때문에 더 좋다
     * - 인퍼페이스 기반 프록시의 단점은 인터페이스가 필요하다는 그 자체이다
     * - 두 방법 모두 적절하게 사용하면 된다
     */
    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace){
        return new OrderControllerConcreteProxy(new OrderControllerV2(orderServiceV2(logTrace)), logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace){
        return new OrderServiceConcreteProxy(new OrderServiceV2(orderRepositoryV2(logTrace)), logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace){
        return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(), logTrace);
    }

}
