package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy.target-class=false"}) //JDK 동적 프록시 사용
@SpringBootTest(properties = {"spring.aop.proxy.target-class=true"}) //CGLIB 동적 프록시 사용
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl; //JDK 동적 프록시 사용 시 의존관계 주입을 받을 수 없음
    //실제로 개발할 때 인터페이스가 있으면 인터페이스를 기반으로 의존관계를 주입 받는게 맞다

    @Test
    void go() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }

}
