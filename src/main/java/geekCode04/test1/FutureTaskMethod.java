package geekCode04.test1;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方法4：使用 Callable 创建带返回值的异步任务，通过实现了 Runnable 和 Future 的子接口（ RunnableFuture 、RunnableScheduledFuture 等）或实现类（FutureTask 等）开启异步任务，
 * 这里使用FutureTask 实现，代码如下：
 */
public class FutureTaskMethod {

    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getSum(10);
            }
        });
        new Thread(task).start();
        try {
            System.out.println("结果为: " + task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
