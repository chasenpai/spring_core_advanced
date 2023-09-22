package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략을 파라미터로 받는 방식의 전략 패턴
     * - 선 조립 후 실행하는 방식이 아닌 Context 를 실행할 때 마다 전략을 파라미터로 전달한다
     * - 실행하는 시점에 원하는 Strategy 를 전달할 수 있어 유연하다
     * - 단점을 꼽자면 실행할 때 마다 전략을 계속 지정해주어야 한다
     */
    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    //익명 내부 클래스 사용
    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직 1 실행"));
        context.execute(() -> log.info("비즈니스 로직 2 실행"));
    }

}
