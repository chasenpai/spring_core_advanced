package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext; //너무 많은 기능을 제공한다
    private final ObjectProvider<CallServiceV2> callServiceProvider; //딱 이거!

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    //프록시 내부 호출 문제를 해결하기 위한 대안2 - 지연 조회
    public void external() {
        log.info("call external");
        CallServiceV2 callServiceV2 = callServiceProvider.getObject(); //실제 사용하는 시점에 스프링 빈을 조회
        callServiceV2.internal(); //외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }

}
