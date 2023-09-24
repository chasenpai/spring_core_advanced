package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    /**
     * 프록시 패턴
     * - 데이터가 한번 조회되면 변하지 않는 데이터라면 어디에 보관해두고
     * - 이미 조회한 데이터를 사용하는 것이 성능상 좋다. 이를 캐시라고 한다
     * - 프록시 패턴의 주요 기능은 접근 제어로, 캐시도 접근 자체를 제어하는 기능 중 하나다
     * - 프록시 패턴의 핵심은 RealSubject 코드와 클라이언트 코드를 전혀 변경하지 않고
     * - 프록시를 도입해서 접근 제어를 했다는 점이다
     * - 클라이언트 입장에서는 프록시 객체가 주입되었는지 실제 객체가 주입되었는지 알 수 없다
     */
    @Test
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject); //CacheProxy 는 target 참조
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy); //클라이언트가 CacheProxy 참조
        client.execute();
        client.execute();
        client.execute();
    }

}
