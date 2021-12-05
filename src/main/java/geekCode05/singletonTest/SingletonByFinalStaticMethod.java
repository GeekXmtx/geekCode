package geekCode05.singletonTest;

/**
 * 1、饿汉式（静态常量）[可用]
 * 优点：写法简单；在类加载的时候就完成了实例化，避免了线程同步的问题；
 * 缺点：在类加载的时候就完成实例化，没有达到懒加载的效果。如果从始至终从没使用过这个实例，
 * 则会造成内存的浪费。
 */
public class SingletonByFinalStaticMethod {
    private final static SingletonByFinalStaticMethod singleton = new SingletonByFinalStaticMethod();
    private SingletonByFinalStaticMethod() {};
    public static SingletonByFinalStaticMethod getInstance() {
        return singleton;
    }

}
