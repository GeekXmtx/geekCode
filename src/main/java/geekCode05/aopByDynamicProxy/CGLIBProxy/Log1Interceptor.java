package geekCode05.aopByDynamicProxy.CGLIBProxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

public class Log1Interceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(object, objects);
        after();
        return result;
    }
    private void before() {
        System.out.println(String.format("log1 start time [%s] ", new Date()));
    }
    private void after() {
        System.out.println(String.format("log1 end time [%s] ", new Date()));
    }
}
