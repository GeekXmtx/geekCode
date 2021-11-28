package geekCode04.test1;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 方法6：CompletableFuture
 * Java 1.8 提供的 CompletableFuture 实现了CompletionStage接口和Future接口，前者是对后者的一个扩展，增加了异步回调、流式处理、多个Future组合处理的能力，
 * 使Java在处理多任务的协同工作时更加顺畅便利。这里使用CompletableFuture.supplyAsync() 创建一个异步任务，代码如下：
 */
public class CompletableFutureMethod {

    public static void main(String[] args) {
        Integer result = CompletableFuture.supplyAsync(()->{return getSum(10);}).thenApplyAsync(v -> getValue(v)).join();
        System.out.println("结果为：" + result);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }

    private static int getValue(int num) {
        return num < 100 ? 100 : num;
    }
}
