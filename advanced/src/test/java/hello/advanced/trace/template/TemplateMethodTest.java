package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogin1;
import hello.advanced.trace.template.code.SubClassLogin2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("result time = {}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 2 실행"); //변하는 부분(핵심 기능)
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime; //변하지 않는 부분(부가 기능)
        log.info("result time = {}", resultTime);
    }

    /**
     * 템플릿 메서드 패턴
     * - 이름 그대로 템플릿을 사용하는 방식으로 템플릿은 기준이 되는 거대한 틀이다
     * - 템플릿이라는 틀에 변하지 않는 부분을 몰아두고, 일부 변하는 부분을 별도로 호출해서 해결한다
     * - 템플릿 메서드 패턴은 부모 클래스에 변하지 않는 템플릿 코드를 두고
     * - 변하는 부분은 자식 클래스에서 상속과 오버라이딩을 통해 처리한다
     * - 템플릿 메서드 패턴은 다형성을 사용해서 변하는 부분과 변하지 않는 부분을 분리하는 방법이다
     */
    @Test
    void templateMethodV1() {

        AbstractTemplate template1 = new SubClassLogin1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogin2();
        template2.execute();
    }

    /**
     * 익명 내부 클래스 사용
     * - 템플릿 메서드 패턴의 단점으로는 상속받는 클래스를 계속 만들어야 하는 것이다
     * - 익명 내부 클래스를 사용하면 이러한 단점을 보완할 수 있다
     */
    @Test
    void templateMethodV2() {

        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        template2.execute();

    }

}
