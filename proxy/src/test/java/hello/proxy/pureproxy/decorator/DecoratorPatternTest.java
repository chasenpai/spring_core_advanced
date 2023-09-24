package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator(){
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    /**
     * 데코레이터 패턴
     * - 프록시로 부가 기능을 추가하는 것을 데코레이터 패턴이라 한다
     * - 원하는 만큼 프록시를 집어 넣고 꾸미는게 데코레이터 패턴의 특징
     */
    @Test
    void decorator1(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    /**
     * 프록시 패턴 vs 데코레이터 패턴
     * - 상황에 따라 둘의 모양이 거의 똑같을 때가 있다
     * - 디자인 패턴에서 더 중요한 것은 해당 패턴의 겉모양이 아닌 패턴을 만든 의도이다
     * - 프록시 패턴은 다른 개체에 대한 접근을 제어하기 위한 대리자를 제공하는 의도
     * - 데코레이터 패턴은 객체에 추가 기능을 동적으로 추가하고 기능 확장을 하기 위한 유연한 대안을 제공하는 의도
     */
    @Test
    void decorator2(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }

}
