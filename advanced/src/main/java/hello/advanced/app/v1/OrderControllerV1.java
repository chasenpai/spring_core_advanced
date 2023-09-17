package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;

    /**
     * 로그 추적기 v1
     * - 모든 public 메서드의 호출과 응답 정보를 로그로 출력
     * - 애플리케이션의 흐름을 변경하지 않음
     * - 메서드 호출에 걸린 시간
     * - 정상 흐름과 예외 흐름 구분
     *
     * 문제점
     * - 메서드 호출의 깊이를 알 수 없다
     * - 트랜잭션 ID로 HTTP 요청을 구분할 수 없다
     */
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId){

        TraceStatus status = null;

        try{
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 반드시 던져줘야 애플리케이션의 흐름에 영향을 주지 않는다
        }
    }

}
