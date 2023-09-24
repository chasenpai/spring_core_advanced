package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject { //프록시도 실제 객체와 모양이 같이야 함

    //클라이언트가 프록시를 호출하면 프록시가 최종적으로 실제 객체를 호출해야 한다
    //따라서 내부에 실제 객체의 참조를 가지고 있어야 하는데 그 대상을 target 이라 한다
    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if(cacheValue == null){ //cacheValue 에 값이 없으면 실제 객체(target)을 호출한다
            cacheValue = target.operation();
        }
        return cacheValue;
    }

}
