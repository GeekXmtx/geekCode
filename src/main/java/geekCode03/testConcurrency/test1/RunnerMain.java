
package geekCode03.testConcurrency.test1;

import java.io.IOException;

/**
 * 中断线程:
 * 线程的thread.interrupt()方法是中断线程，将会设置该线程的中断状态位，即设置为true，
 * 中断的结果线程是死亡、还是等待新的任务或是继续运行至下一步，就取决于这个程序本身。
 * 线程会不时地检测这个中断标示位，以判断线程是否应该被中断（中断标示值是否为true）。
 * 它并不像stop方法那样会中断一个正在运行的线程。
 *
 *
 * 判断线程是否被中断:
 *
 * 判断某个线程是否已被发送过中断请求，
 * 使用Thread.currentThread().isInterrupted()方法（因为它将线程中断标示位设置为true后，
 * 不会立刻清除中断标示位，即不会将中断标设置为false），
 * 而不要使用thread.interrupted()（该方法调用后会将中断标示位清除，
 * 即重新设置为false）方法来判断，下面是线程在循环中时的中断方式：
 */

public class RunnerMain {

    public static void main(String[] args) throws IOException {

        Runner1 runner1 = new Runner1();
        Thread thread1 = new Thread(runner1);

        Runner2 runner2 = new Runner2();
        Thread thread2 = new Thread(runner2);

        thread1.start();
        thread2.start();

        thread2.interrupt();  // i = true

        System.out.println(Thread.activeCount());
        
        Thread.currentThread().getThreadGroup().list();
        System.out.println(Thread.currentThread().getThreadGroup().getParent().activeGroupCount());
        Thread.currentThread().getThreadGroup().getParent().list();
    
        
    }
}
