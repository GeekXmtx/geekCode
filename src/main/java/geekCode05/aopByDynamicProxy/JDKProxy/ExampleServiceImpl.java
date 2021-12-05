package geekCode05.aopByDynamicProxy.JDKProxy;

public class ExampleServiceImpl implements ExampleService{
    @Override
    public void info() {
        System.out.println("Print example info");
    }
}
