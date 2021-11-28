package geekCode04.test1;

/**
 * 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这
 *个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。
 *
 * 方式1：使用volatile + join：
 *
 * volatile使用场景:volatile变量独立于程序状态、变量状态
 *
 *     volatile关键字不保证原子性；
 *     对变量写操作不依赖当前值；
 *     该变量没有包含在具有其他变量的不变式中;
 *
 * 该关键字保证有序性（禁止指令重排），可见性（volatile的写操作会向主存刷新），不保证原子性
 */
public class VolatileJoinMethod {

    private static volatile Integer sum = null;
    public static void main(String[] args) throws InterruptedException {
        final VolatileJoinMethod method = new VolatileJoinMethod();
        Thread thread = new Thread(() -> {
            sum = method.getSum(10);
        });
        thread.start();
        thread.join();
        System.out.println("结果为："+ sum);
    }

    private int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
