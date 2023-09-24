package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        /**
         * 클래스 기반 프록시의 단점
         * - 자바 기본 문법에 의해 자식 클래스를 생성할 때 super() 를 항상 호출해야 한다
         * - 이 부분을 생략하면 기본 생성자가 호출되지만 부모 클래스에 기본 생성자가 없다면
         * - 파라미터를 넘겨 super(...) 를 호출해야 한다
         * - 하지만 프록시는 부모 객체의 기능을 사용하지 않기 때문에 null 을 넘겨줘도 된다
         * - 인터페이스 기반 프록시는 이 고민을 할 필요가 없다
         */
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {

        TraceStatus status = null;

        try{
            status = logTrace.begin("OrderService.request()");
            target.orderItem(itemId); //target 호출
            logTrace.end(status);
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }

}
