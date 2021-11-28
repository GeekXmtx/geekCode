package geekCode04.test1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方式10：CyclicBarrier
 * 利用CyclicBarrier类可以实现一组线程相互等待，当所有线程都到达某个屏障点后再进行后续的操作。
 * 实现代码：
 */
public class CyclicBarrierMethod {

    static int sum = 0; // 全局静态属性记录计算结果

    public static void main(String[] args) throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            sum = getSum(10);
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // 确保  拿到result 并输出
        barrier.await();
        System.out.println("结果为："+sum);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
