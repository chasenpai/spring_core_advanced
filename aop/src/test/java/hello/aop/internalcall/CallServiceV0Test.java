package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void external() {
        callServiceV0.external(); //내부에서 internal() 호출 시 자기 자신(this)의 내부 메서드를 호출 > AOP 적용 X
    }

    @Test
    void internal() {
        callServiceV0.internal();
    }

}