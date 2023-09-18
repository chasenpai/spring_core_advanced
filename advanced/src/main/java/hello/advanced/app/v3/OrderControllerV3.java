package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;

    /**
     * 로그 추적기 v3
     * - traceHolder 덕분에 traceId를 추가 파라미터로 넘길 필요 없는 깔끔한 코드가 되었다
     *
     * 문제점
     * - 동시성 이슈 발생
     * - 동시에 여러번 요청하면 트랜잭션 ID가 섞여 버린다
     * - FieldLogTrace 는 싱글톤 객체로, traceHolder 를 여러 쓰레드가 동시에 접근하기 때문
     * - 스프링 빈 처럼 싱글톤 객체의 필드를 변경하며 사용할 때 동시성 문제를 조심해야 한다
     */
    private final LogTrace trace;

    @GetMapping("/v3/request")
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
