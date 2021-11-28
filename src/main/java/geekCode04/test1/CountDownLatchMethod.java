package geekCode04.test1;

import java.util.concurrent.CountDownLatch;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方式9：CountDownLatch方式
 * CountDownLatch是在java1.5被引入，跟它一起被引入的工具类还有CyclicBarrier、Semaphore、ConcurrentHashMap和BlockingQueue，都在JUC工具包中。
 * CountDownLatch是通过一个计数器来实现的，计数器的初始化值为线程的数量。
 * 每当一个线程完成了自己的任务后，计数器的值就相应得减1。当计数器到达0时，表示所有的线程都已完成任务，然后在闭锁上等待的线程就可以恢复执行任务。
 */
public class CountDownLatchMethod {

    static int sum = 0; // 全局静态属性记录计算结果

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            sum = getSum(10);
            countDownLatch.countDown();
        }).start();
        // 确保  拿到result 并输出
        countDownLatch.await();
        System.out.println("结果为："+sum);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
