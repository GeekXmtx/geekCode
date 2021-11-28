package geekCode04.test1;

import java.util.concurrent.Semaphore;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方式11：Semaphore
 * Semaphore 通常我们叫它信号量， 可以用来控制同时访问特定资源的线程数量，通过协调各个线程，以保证合理的使用资源。
 *
 * 代码如下：
 */
public class SemaphoreMethod {

    static int sum = 0; // 全局静态属性记录计算结果

    public static void main(String[] args) throws Exception {
        Semaphore semaphore = new Semaphore(0);

        new Thread(() -> {
            sum = getSum(10);
            semaphore.release();
        }).start();
        // 确保  拿到 result 并输出
        semaphore.acquire();
        int result = sum;
        System.out.println("结果为："+result);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }

}
