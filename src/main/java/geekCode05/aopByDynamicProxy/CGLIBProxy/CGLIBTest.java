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
