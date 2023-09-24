package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator extends DecoratorAbstract implements Component {

    public MessageDecorator(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = component.operation();
        String decoResult = "**** " + result + " ****";
        log.info("MessageDecorator 적용 전 = {}, 적용 후 = {}", result, decoResult);
        return decoResult;
    }

}
