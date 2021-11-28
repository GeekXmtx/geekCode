package geekCode04.test1;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方法5：利用线程池（ExecutorService、ScheduledExecutorService 等）提交一个有返回值的任务（可以用invokeAll()、invokeAny()、submit() 等方法），
 * 这里使用executorService.submit()，代码如下：
 */
public class ThreadPoolMethod {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getSum(10);
            }
        });
        executor.submit(task);
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
