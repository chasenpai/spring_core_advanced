package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;

    /**
     * 로그 추적기 v4
     * - 템플릿 메서드 패턴 적용
     * - 핵심 기능과 부가 기능을 분리
     * - 단순히 템플릿 메서드 패턴을 적용하면서 코드 몇줄을 줄인게 전부가 아닌
     * - 단일 책임 원칙(SRP)를 지키게 되면서 변경에 쉽게 대처할 수 있는 구조를 만든 것
     *
     * 단점
     * - 템플릿 메서드 패턴은 상속을 사용하기 때문에 그에 따른 단점까지 그대로 안고가야 한다
     * - 자식 클래스는 부모 클래스를 강하게 의존하게 되고, 부모 클래스의 기능을 사용하지 않더라도
     * - 부모 클래스를 수정하면 자식 클래스에도 영향을 줄 수 있다
     * - 또한 상속을 사용하기 때문에 별도의 클래스나 익명 내부 클래스를 만들어야 하는 번거로움이 있다
     * -> 전략 패턴(Strategy Pattern)을 사용하면 이 문제를 해결할 수 있다
     */
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId){

        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderController.request()");
    }

}
