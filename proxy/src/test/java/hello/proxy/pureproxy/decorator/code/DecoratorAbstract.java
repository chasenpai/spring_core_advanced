package hello.proxy.pureproxy.decorator.code;

public abstract class DecoratorAbstract {

    /**
     * 데코레이터 패턴에서 꾸며주는 역할을 하는 Decorator 들은 스스로 존재할 수 없고
     * 내부에 호출 대상인 Component 를 가지고 있어야 있고, 항상 호출해야 하는 부분이 중복된다
     * 중복 제거를 위해 추상 클래스를 도입할 수 있다
     */
    public Component component;

    public DecoratorAbstract(Component component) {
        this.component = component;
    }

}
