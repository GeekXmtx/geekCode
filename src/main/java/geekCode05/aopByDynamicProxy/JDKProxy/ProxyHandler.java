package geekCode05.aopByDynamicProxy.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 执行相关业务
        before();
        Object result = method.invoke(object, args);
        after();
        return result;
    }
    private Object object;
    public ProxyHandler(Object object) {this.object = object;}
    private void before() {
        System.out.println("proxy before method");
    }
    private void after() {
        System.out.println("proxy after method");
    }
}
