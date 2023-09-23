package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    /**
     * 템플릿 콜백 패턴
     * - 변하는 부분을 파라미터로 넘겨 코드를 실행시켜 처리한다
     * - 다른 코드의 인수로 실행 가능한 코드를 넘겨주는 것을 콜백(Callback)이라 한다
     * - 스프링에서 Context 를 실행할 때 마다 Strategy 를 넘겨주는 전략 패턴을 템플릿 콜백 패턴이라고 부른다
     * - 스프링에서 xxxTemplate 은 템플릿 콜백 패턴으로 만들어져 있다고 생각하면 된다
     */
    @Test
    void callbackV1(){
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직 1 실행"));
        template.execute(() -> log.info("비즈니스 로직 2 실행"));
    }

}
