package geekCode04.test1;

import java.util.concurrent.locks.LockSupport;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方式8：LockSupportMethod park/unpark
 * LockSupport类，是JUC包中的一个工具类，是用来创建锁和其他同步类的基本线程阻塞原语。
 *  与Object的wait/notify的区别主要有两点：
 *  （1）wait/notify都是Object中的方法,在调用这两个方法前必须先获得锁对象，但是park/unpark不需要获取某个对象的锁就可以锁住线程。
 *  （2）notify只能随机选择一个线程唤醒，无法唤醒指定的线程，unpark却可以唤醒一个指定的线程。
 *  使用 LockSupportMethod 的实现代码如下：
 */
public class LockSupportMethod {
    static int sum = 0; // 全局静态属性记录计算结果

    public static void main(String[] args) throws Exception {

        final Thread mainThread = Thread.currentThread();
        new Thread(() -> {
            sum = getSum(10);
            LockSupport.unpark(mainThread);
        }).start();
        // 确保  拿到result 并输出
        LockSupport.park();
        System.out.println("结果为："+sum);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
