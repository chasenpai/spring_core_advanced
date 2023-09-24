package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    /**
     * 인터페이스 기반 프록시
     * - 프록시를 실제 스프링 빈 대신 등록한다
     * - 프록시는 내부에 실제 객체를 참조하고 있다 proxy -> target
     * - 스프링 컨테이너는 실제 객체가 아닌 프록시 객체를 스프링 빈으로 관리한다
     * - 실제 객체는 스프링 컨테이너와 상관 없이 프록시 객체를 통해서 참조될 뿐이다
     */
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace){
        return new OrderControllerInterfaceProxy(new OrderControllerV1Impl(orderService(logTrace)), logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){
        return new OrderServiceInterfaceProxy(new OrderServiceV1Impl(orderRepository(logTrace)), logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace){
        return new OrderRepositoryInterfaceProxy(new OrderRepositoryV1Impl(), logTrace);
    }

}
