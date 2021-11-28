package geekCode04.test1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这
 *个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。
 *
 * 方式2：使用Automic(原子类) + join：
 *
 * 原子类底层即采用了CAS + 自旋锁来解决线程安全问题
 */
public class AutomicJoinMethod {

    private static AtomicInteger atomSum = new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
        final AutomicJoinMethod method = new AutomicJoinMethod();
        Thread thread = new Thread(() -> {
            atomSum.set(method.getSum(10));
        });
        thread.start();
        thread.join();
        System.out.println("结果为："+ atomSum);
    }

    private int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
