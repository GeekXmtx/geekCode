package geekCode03.testConcurrency.test1;

/**
 * 对Thread.interrupt()的理解：
 *
 * 1.一个线程不应该被其他线程中断
 * 2.Thread.interrupt()的作用不是中断某个线程，而是通知一个线程应该中断了。
 * 3.被通知中断的线程具体是中断还是选择继续运行由该线程自己决定。
 * 4.具体来说，当对一个线程调用interrupt()方法时，
 * 1）当线程处于sleep()、wait()、join()等状态时，该线程将立即退出阻塞状态，然后抛出一个InterruptedException异常
 * 2）当线程处于正常状态时，该线程的中断标志位将被置为true，仅此而已。线程将继续正常运行。
 * 5.interrupt()方法并不会中断线程，需要线程本身配合才行。
 * 也就是说，一个线程如果有中断的需求，可以这样做：
 * 1）在运行时，经常检查自己的中断标志位，如果被设置了中断标志，就自动停止运行
 * 2）在调用阻塞方法[sleep()、wait()、join()]时，正确处理InterruptedException异常（如catch异常后，主动退出）
 * 6.Thread.isInterrupted()的作用是判断是否被中断。
 * 7.Thread.interrupted()的作用是判断是否被中断，并清除标志位。目的是下次继续检测标志位。
 * 如果一个线程被设置中断标志后，选择结束线程那么自然不存在下次的问题，而如果一个线程被设置中断标识后，
 * 进行了一些处理后选择继续进行任务，而且这个任务也是需要被中断的，那么当然需要清除标志位。
 */
public class Runner2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner2运行状态——————————" + i);
        }

        boolean result = Thread.currentThread().isInterrupted();

        boolean result1 = Thread.interrupted(); // 重置状态
        
        boolean result3 = Thread.currentThread().isInterrupted();

        System.out.println("Runner2.run result ===>" + result);
        System.out.println("Runner2.run result1 ===>" + result1);
        System.out.println("Runner2.run result3 ===>" + result3);
        
        
    }
}
