package hello.advanced.app.v3;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId){

        TraceStatus status = null;

        try{
            status = trace.begin("OrderService.request()");
            orderRepository.sava(itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 반드시 던져줘야 애플리케이션의 흐름에 영향을 주지 않는다
        }
    }

}
