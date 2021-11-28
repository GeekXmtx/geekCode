package geekCode04.test1;

/**
 * 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这
 *个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。
 *
 * 方式3：使用synchronized + notify
 */
public class SynchronizedNotifyMethod {

    private static volatile Integer sum = 0;
    public static void main(String[] args) throws InterruptedException {
        final SynchronizedNotifyMethod method = new SynchronizedNotifyMethod();
        Thread thread = new Thread(() -> {
            sum = method.getSum(10);
        });
        thread.start();
        synchronized (thread){
            thread.wait();
        }
        System.out.println("结果为："+ sum);
    }

    private int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
