package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){

        Hello target = new Hello();

        //공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);
        //공통 로직 1 종료

        //공통 로직 2 시작
        log.info("start");
        String result2 = target.callA();
        log.info("result = {}", result2);
        //공통 로직 2 종료
    }

    /**
     * 자바 리플렉션
     * - 클래스나 메서드의 메타정보를 동적으로 획득할 수 있다
     * - 리플렉션을 통해서 클래스와 메서드의 메타정보를 추상화하고 공통 로직을 만들 수 있다
     * - 하지만 런타임에 동작하기 때문에 컴파일 시점에 오류를 잡을 수 없다
     * - 리플렉션은 일반적으로 사용하지 않는 것이 좋다
     */
    @Test
    void reflection1() throws Exception {

        //클래스 메타정보 획득 - 내부 클래스는 $ 사용
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        //callA 메서드 메타정보 획득
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target); //실제 인스턴스의 메서드 호출
        log.info("result 1 = {}", result1);

        //callB 메서드 메타정보 획득
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2 1 = {}", result2);
    }

    @Test
    void reflection2() throws Exception {

        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target); //리플렉션을 사용한 공통 로직
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello {

        public String callA(){
            log.info("call A");
            return "A";
        }

        public String callB(){
            log.info("call B");
            return "B";
        }

    }

}
