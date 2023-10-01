package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
public class BeanPostPrTest {

    @Test
    void basicConfig(){

        ApplicationContext context = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        //beanA 이름으로 B 객체가 빈으로 등록된다
        B beanB = context.getBean("beanA", B.class);
        beanB.helloB();

        //A는 빈으로 등록x
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(A.class));
    }

    @Configuration
    static class BeanPostProcessorConfig {

        @Bean(name = "beanA")
        public A a(){
            return new A();
        }

        @Bean
        public AToBPostProcessor aToBPostProcessor(){
            return new AToBPostProcessor();
        }

    }

    /**
     * 빈 후처리기 BeanPostProcessor
     * - 빈을 조작하고 변경할 수 있는 후킹 포인트
     * - 빈 객체를 조작하거나 다른 객체로 바꿔버릴 수 있을 정도로 막강하다
     * - 일반적으로 컴포넌트 스캔의 대상이 되는 빈들은 중간에 조작할 방법이 없지만
     * - 빈 후처리기를 사용하면 개발자가 등록하는 모든 빈을 중간에 조작할 수 있다
     * - 즉 빈 객체를 프록시로 교체하는 것도 가능하다
     */
    static class AToBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

            log.info("beanName = {}, bean = {}", beanName, bean);

            if(bean instanceof A){
                return new B();
            }
            return bean;
        }
    }

    static class A {

        public void helloA(){
            log.info("hello A");
        }

    }

    static class B {

        public void helloB(){
            log.info("hello B");
        }

    }

}
