package geekCode05.singletonTest;

/**
 * 8、枚举[推荐用]
 *
 * 优缺点：
 * 1） 这种借助JDK1.5中添加的枚举来实现单例模式。不仅能避免多线程同步问题，
 * 而且还能防止反序列化重新创建新的对象。
 * 2） 这种方式Effective Java作者Josh Bloch提倡的方式。
 */
public class SingletonByEnum {
    public static void main(String[] args) {
        Singleton instanceOne = Singleton.INSTANCE;
        Singleton instanceTwo = Singleton.INSTANCE;
        //true
        System.out.println(instanceOne == instanceTwo);
        instanceOne.sayOK();
    }

    enum Singleton {
        /**
         * 枚举属性
         */
        INSTANCE;
        public void sayOK() {
            System.out.println("ok！");
        }
    }
}
