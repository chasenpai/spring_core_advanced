package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    //프록시 내부 호출 문제를 해결하기 위한 대안3 - 구조 변경(가장 권장)
    public void external() {
        log.info("call external");
        internalService.internal();
    }

}
