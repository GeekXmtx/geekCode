package geekCode04.test1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方式7：Lock + Condition await/signal
 *
 * wait/notify 方式有个缺点是不能保证 wait 和 notify 调用的先后顺序，如果 wait 执行比 notify 延后，则主线程会一直等。
 * 使用 Java 并发包（JUC）的 Lock 与 Condition 的 await/signal 机制可以做到精确唤醒，代码如下
 */
public class LockConditionMethod {

    static final Lock lock = new ReentrantLock(); // 定义锁
    static final Condition condition = lock.newCondition(); // Condition

    public static void main(String[] args) throws Exception {

        AtomicInteger atomSum = new AtomicInteger();
        new Thread(() -> {
            lock.lock();
            try {
                atomSum.set(getSum(10));
                condition.signal(); // 通知主线程可以获取值了
            } finally {
                lock.unlock();
            }
        }).start();

        lock.lock();
        try {
            condition.await();
            System.out.println("结果为："+atomSum.get());
        } finally {
            lock.unlock();
        }
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
