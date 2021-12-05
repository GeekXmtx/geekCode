package geekCode05.singletonTest;

/**
 * 进行了两次if (singleton == null)检查，这样就可以保证线程安全了。
 * 这样，实例化代码只用执行一次，后面再次访问时，判断if (singleton == null)，
 * 直接return实例化对象。
 * 优点：线程安全；延迟加载；效率较高。
 */
public class SingletonByDoubleCheckMethod {
    private static volatile SingletonByDoubleCheckMethod singleton;
    private SingletonByDoubleCheckMethod() {};
    public static SingletonByDoubleCheckMethod getInstance() {
        if(singleton == null) {
            synchronized (SingletonByDoubleCheckMethod.class) {
                if(singleton == null) {
                    singleton = new SingletonByDoubleCheckMethod();
                }
            }
        }
        return singleton;
    }
}
