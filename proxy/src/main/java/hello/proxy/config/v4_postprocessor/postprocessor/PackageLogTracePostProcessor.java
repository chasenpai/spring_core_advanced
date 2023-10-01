package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        log.info("beanName = {}, bean = {}", beanName, bean.getClass());

        String packageName = bean.getClass().getPackageName();
        if(!packageName.startsWith(basePackage)){
            //프록시 적용 대상이 아니면 원본 빈 반환
            //스프링이 기본으로 제공하는 빈들 중엔 프록시 객체로 만들 수 없는 빈들도 존재
            return bean;
        }

        //프록시 대상이면 프록시를 만들어 반환
        ProxyFactory factory = new ProxyFactory(bean);
        factory.addAdvisor(advisor);
        Object proxy = factory.getProxy();
        log.info("create proxy target = {}, proxy = {}", bean.getClass(), proxy.getClass());

        return proxy;
    }
}
