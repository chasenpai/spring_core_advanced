package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("result time = {}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 2 실행"); //변하는 부분(핵심 기능)
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime; //변하지 않는 부분(부가 기능)
        log.info("result time = {}", resultTime);
    }

    /**
     * 전략 패턴
     * - 변하지 않는 부분을 Context 라는 곳에 두고, 변하는 부분을 Strategy 라는
     * - 인터페이스로 만들어 구현하도록 해서 문제를 해결한다
     * - 상속이 아닌 위임으로 문제를 해결하는 것이다
     * - 전략 패턴에서 Context 가 템플릿 역할을 하고 Strategy 는 변하는 알고리즘 역할을 한다
     */
    @Test
    void strategyV1(){

        //필드에 전략을 보관하는 방식
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    //익명 내부 클래스 사용
    @Test
    void strategyV2(){

        //Strategy 인터페이스는 메서드가 1개만 있으므로 람다로 사용 가능
        Strategy strategyLogic1 = () -> log.info("비즈니스 로직 1 실행");
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = () -> log.info("비즈니스 로직 2 실행");
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV3(){
        /**
         * 선 조립, 후 실행
         * - Context 와 Strategy 를 실행 전에 원하는 모양으로 조립해두는 방식으로 선 조립, 후 실행 방법에 적합하다
         * - 실행하는 시점에 이미 조립이 끝났기 때문에 전략을 신경쓰지 않고 단순히 실행만 하면 된다
         * - 이는 스프링이 애플리케이션 로딩 시점에 의존관계 주입을 모두 한 뒤 실제 요청을 처리하는 것과 같은 원리이다
         * - 하지만 한번 조립하고 나면 Strategy 를 변경하기 번거롭고, 싱글톤으로 사용할 땐 동시성 이슈가 발생할 수 있다
         */
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직 1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직 2 실행"));
        context2.execute();
    }

}
