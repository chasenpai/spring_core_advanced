package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    /**
     * 로그 추적기 v2
     * - 메서드의 깊이 표현
     * - HTTP 요청 구분
     *
     * 문제점
     * - HTTP 요청을 구분하고 깊이를 표현하기 위해 TraceId 동기화가 필요하다
     * - TraceId 동기화를 위해 관련 메서드의 모든 파라미터를 수정해야 한다
     * - 로그를 처음 시작할 땐 begin(), 처음이 아닐땐 beginSync() 를 호출해야 한다
     * - 만약 컨트롤러가 아닌 서비스에서 처음으로 호출한다면 파라미터로 넘길 TraceId 가 없다
     */
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId){

        TraceStatus status = null;

        try{
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 반드시 던져줘야 애플리케이션의 흐름에 영향을 주지 않는다
        }
    }

}
