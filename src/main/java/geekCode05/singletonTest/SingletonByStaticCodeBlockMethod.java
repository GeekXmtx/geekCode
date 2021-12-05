package geekCode05.singletonTest;
/**
 * 2、饿汉式（静态代码块）[可用]
 * 该方式同方式一类似，优缺点一致，只不过将类实例化的过程放在了静态代码块中，
 * 也是在类加载的时候，就执行静态代码块中的代码，初始化类的实例。
 */
public class SingletonByStaticCodeBlockMethod {
    private static SingletonByStaticCodeBlockMethod singleton;
    static {
        singleton = new SingletonByStaticCodeBlockMethod();
    }
    private  SingletonByStaticCodeBlockMethod() {};
    public SingletonByStaticCodeBlockMethod getInstance() {
        return singleton;
    }

}
