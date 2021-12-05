# 1.（选做）使 Java 里的动态代理，实现一个简单的 AOP。

## 总结：

[link](文档：静态代理、动态代理总结.note
链接：http://note.youdao.com/noteshare?id=879e58616f6ee1225a1faedfcff34662&sub=285BDE0CB7544E27AD72A7BD34022969)

## 实现：

### 使用JDK动态代理实现：

```
package geekCode05.aopByDynamicProxy.JDKProxy;

public interface ExampleService {
    /**
     * 打印信息
     */
    void info();
}

```


```
package geekCode05.aopByDynamicProxy.JDKProxy;

public class ExampleServiceImpl implements ExampleService{
    @Override
    public void info() {
        System.out.println("Print example info");
    }
}

```


```
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

```


```
package geekCode05.aopByDynamicProxy.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *  注意Proxy.newProxyInstance()方法接受三个参数：
 *     ClassLoader loader:指定当前目标对象使用的类加载器,获取加载器的方法是固定的
 *     Class<?>[] interfaces:指定目标对象实现的接口的类型,使用泛型方式确认类型
 *     InvocationHandler:指定动态处理器，执行目标对象的方法时,会触发事件处理器的方法
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        ExampleServiceImpl exampleService = new ExampleServiceImpl();
        ClassLoader classLoader = exampleService.getClass().getClassLoader();
        Class[] interfaces = exampleService.getClass().getInterfaces();
        InvocationHandler handler = new ProxyHandler(exampleService);

        ExampleService proxy = (ExampleService) Proxy.newProxyInstance(classLoader, interfaces, handler);
        proxy.info();
    }
}

```

### 使用CGLIB动态代理实现：


```
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

```


```
package geekCode05.aopByDynamicProxy.CGLIBProxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

public class Log2Interceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(object, objects);
        after();
        return result;
    }
    private void before() {
        System.out.println(String.format("log2 start time [%s] ", new Date()));
    }
    private void after() {
        System.out.println(String.format("log2 end time [%s] ", new Date()));
    }
}

```

```
package geekCode05.aopByDynamicProxy.CGLIBProxy;

import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * 回调过滤器: 在CGLib回调时可以设置对不同方法执行不同的回调逻辑，或者根本不执行回调。
 */
public class DaoFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if ("select".equals(method.getName())) {
            return 0;   // Callback 列表第1个拦截器
        }
        return 1;   // Callback 列表第2个拦截器，return 2 则为第3个，以此类推
    }
}

```


```
package geekCode05.aopByDynamicProxy.CGLIBProxy;

public class UserDao {
    public void select() {
        System.out.println("UserDao 查询 selectById");
    }
    public void update() {
        System.out.println("UserDao 更新 update");
    }
}

```


```
package geekCode05.aopByDynamicProxy.CGLIBProxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

/**
 * CGLIB 创建动态代理类的模式是：
 *     查找目标类上的所有非final的public类型的方法定义；
 *     将这些方法的定义转换成字节码；
 *     将组成的字节码转换成相应的代理的class对象；
 *     实现 MethodInterceptor接口，用来处理对代理类上所有方法的请求
 */
public class CGLIBTest {
    public static void main(String[] args) {
        Log1Interceptor log1Interceptor = new Log1Interceptor();
        Log2Interceptor log2Interceptor = new Log2Interceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDao.class);   // 设置超类，cglib是通过继承来实现的
        //NoOp.INSTANCE是一个空拦截器，不做任何处理
        enhancer.setCallbacks(new Callback[]{log1Interceptor, log2Interceptor, NoOp.INSTANCE});   // 设置多个拦截器
        enhancer.setCallbackFilter(new DaoFilter());

        UserDao proxy = (UserDao) enhancer.create();   // 创建代理类
        proxy.select();
        proxy.update();
    }
}

```

