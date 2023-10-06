package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProxyCastingTest {

    /**
     * JDK 동적 프록시는 대상 객체인 MemberServiceImpl 로 캐스팅 불가능
     * CGLIB 프록시는 대상 객체인 MemberServiceImpl 로 캐스팅 가능
     */
    @Test
    void jdkProxy() {

        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);

        //프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy =  (MemberService) proxyFactory.getProxy();
        //JDK 동적 프록시를 구현 클래스로 캐스팅 시 실패
        //MemberService 인터페이스 기반으로 생성됐기 때문에 MemberServiceImpl 이 어떤 것인지 전혀 알지 못한다
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy() {

        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        //CGLIB 프록시를 구현 클래스로 캐스팅 시 성공
        MemberService memberServiceProxy =  (MemberService) proxyFactory.getProxy();
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }

}
