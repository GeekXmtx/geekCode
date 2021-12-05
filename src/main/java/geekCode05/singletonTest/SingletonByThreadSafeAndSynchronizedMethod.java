package geekCode05.singletonTest;

/**
 * 4、懒汉式(线程安全，同步方法)[不推荐用]
 * 优点:解决了前三种实现方式线程不安全的问题，对getInstance()方法进行了线程同步。
 * 缺点：效率太低了，每个线程在想获得类的实例的时候，执行getInstance()方法都要进行同步。
 * 而其实这个方法只执行一次实例化代码就够了，后面的想获得该类实例，直接return。
 * 方法进行同步，效率太低，需要改进。
 */
public class SingletonByThreadSafeAndSynchronizedMethod {
    private static SingletonByThreadSafeAndSynchronizedMethod singleton;
    private SingletonByThreadSafeAndSynchronizedMethod() {};
    public static synchronized SingletonByThreadSafeAndSynchronizedMethod getInstance() {
        if(singleton == null) {
            singleton = new SingletonByThreadSafeAndSynchronizedMethod();
        }
        return singleton;
    }
}
