==2.（必做）思考有多少种方式，在main函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程? 写出你的方法，越多越好。==

####  方式1：使用volatile + join：

```
package geekCode04.test1;

/**
 * volatile使用场景:volatile变量独立于程序状态、变量状态
 *
 *     volatile关键字不保证原子性；
 *     对变量写操作不依赖当前值；
 *     该变量没有包含在具有其他变量的不变式中;
 *
 * 该关键字保证有序性（禁止指令重排），可见性（volatile的写操作会向主存刷*新），不保证原子性
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

```

#### 方式2：使用Automic(原子类) + join：

```
package geekCode04.test1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类底层即采用了CAS + 自旋锁来解决线程安全问题
 */
public class AutomicJoinMethod {

    private static AtomicInteger atomSum = new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
        final AutomicJoinMethod method = new AutomicJoinMethod();
        Thread thread = new Thread(() -> {
            atomSum.set(method.getSum(10));
        });
        thread.start();
        thread.join();
        System.out.println("结果为："+ atomSum);
    }

    private int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}
```

#### 方式3：使用synchronized + notify：


```
package geekCode04.test1;

/**
 * 方式3：使用synchronized + notify：
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
```

#### 方式4：使用Callable + Thread：

```
package geekCode04.test1;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
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

```

#### 方式5：使用线程池

```
package geekCode04.test1;

import java.util.concurrent.*;

/**
 * 利用线程池（ExecutorService、ScheduledExecutorService 等）提交一个有返回值的任务（可以用invokeAll()、invokeAny()、submit() 等方法），这里使用executorService.submit()，代码如下：
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

```

#### 方式6：使用CompletableFuture

```
package geekCode04.test1;

import java.util.concurrent.*;

/**
 *
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

```

#### 方式7：使用Lock + Condition await/signal

```
package geekCode04.test1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * wait/notify 方式有个缺点是不能保证 wait 和 notify 调用的先后顺序，如果 wait 执行比 notify 延后，则主线程会一直等。
 * 使用 Java 并发包（JUC）的 Lock 与 Condition 的 await/signal 机制可以做到精确唤醒，代码如下
 */
public class LockConditionMethod {

    static final Lock lock = new ReentrantLock(); // 定义锁
    static final Condition condition = lock.newCondition(); // Condition

    public static void main(String[] args) throws Exception {

        AtomicInteger atomSum = new AtomicInteger();
        new Thread(() -> {
            lock.lock();
            try {
                atomSum.set(getSum(10));
                condition.signal(); // 通知主线程可以获取值了
            } finally {
                lock.unlock();
            }
        }).start();

        lock.lock();
        try {
            condition.await();
            System.out.println("结果为："+atomSum.get());
        } finally {
            lock.unlock();
        }
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}

```

#### 方式8：使用LockSupport park/unpark


```
package geekCode04.test1;

import java.util.concurrent.locks.LockSupport;

/**
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

```

#### 方式9：使用CountDownLatch

```
package geekCode04.test1;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch方式:
 * CountDownLatch是在java1.5被引入，跟它一起被引入的工具类还有CyclicBarrier、Semaphore、ConcurrentHashMap和BlockingQueue，都在JUC工具包中。
 * CountDownLatch是通过一个计数器来实现的，计数器的初始化值为线程的数量。
 * 每当一个线程完成了自己的任务后，计数器的值就相应得减1。当计数器到达0时，表示所有的线程都已完成任务，然后在闭锁上等待的线程就可以恢复执行任务。
 */
public class CountDownLatchMethod {

    static int sum = 0; // 全局静态属性记录计算结果

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            sum = getSum(10);
            countDownLatch.countDown();
        }).start();
        // 确保  拿到result 并输出
        countDownLatch.await();
        System.out.println("结果为："+sum);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}

```

#### 方式10：使用CyclicBarrier


```
package geekCode04.test1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
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

```

#### 方式11：使用Semaphore


```
package geekCode04.test1;

import java.util.concurrent.Semaphore;

/**
 *
 * 方式11：Semaphore
 * Semaphore 通常我们叫它信号量，可以用来控制同时访问特定资源的线程数
 *量，通过协调各个线程，以保证合理的使用资源。
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

```

## Semaphore案例：

 实现一个简单的登录队列，通过Semaphore来限制系统中的用户数：

```
package geekCode04.test1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 实现一个简单的登录队列，通过Semaphore来限制系统中的用户数：
 */
public class SemaphoreDemo {
    public static void main(String[] args) {

        //允许最大的登录数
        int slots=10;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        //线程池模拟登录
        for (int i = 1; i <= slots; i++) {
            final int num=i;
            executorService.execute(()->{
                if (loginQueue.tryLogin()){
                    System.out.println("用户:"+num+"登录成功！");
                }else {
                    System.out.println("用户:"+num+"登录失败！");
                }
            });
        }
        executorService.shutdown();
        System.out.println("当前可用许可证数："+loginQueue.availableSlots());

        //此时已经登录了10个用户，再次登录的时候会返回false
        if (loginQueue.tryLogin()){
            System.out.println("登录成功！");
        }else {
            System.out.println("系统登录用户已满，登录失败！");
        }
        //有用户退出登录
        loginQueue.logout();

        //再次登录
        if (loginQueue.tryLogin()){
            System.out.println("登录成功！");
        }else {
            System.out.println("系统登录用户已满，登录失败！");
        }

    }
    static class LoginQueueUsingSemaphore{

        private Semaphore semaphore;

        /**
         * @param slotLimit
         */
        public LoginQueueUsingSemaphore(int slotLimit){
            semaphore=new Semaphore(slotLimit);
        }

        boolean tryLogin() {
            //获取一个凭证
            return semaphore.tryAcquire();
        }

        void logout() {
            semaphore.release();
        }

        int availableSlots() {
            return semaphore.availablePermits();
        }
    }
}

用户:1登录成功！
当前可用许可证数：9
登录成功！
登录成功！
用户:2登录成功！
用户:3登录成功！
用户:7登录成功！
用户:4登录成功！
用户:10登录成功！
用户:6登录成功！
用户:9登录成功！
用户:8登录成功！
用户:5登录失败！

```

#### 方式12：使用BlockingQueue

```
package geekCode04.test1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 当队列中填满数据的情况下，生产者端的所有线程都会被自动阻塞（挂起），直到队列中有空的位置，线程被自动唤醒
 *
 * BlockingQueue的核心方法：
 * 　　1.放入数据
 * 　　　　（1）offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.（本方法不阻塞当前执行方法
 *  的线程）；　　　　　　
*      　　（2）offer(E o, long timeout, TimeUnit unit)：可以设定等待的时间，如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败。
 * 　　　　（3）put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
 * 　　2. 获取数据
 * 　　　　（1）poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null;
 * 　　　　（2）poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，队列一旦有数据可取，则立即返回队列中的数据。否则知道时间
 * 超时还没有数据可取，返回失败。
 * 　　　　（3）take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入;
 * 　　　　（4）drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数），通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
 */

public class BlockingQueueMethod {
    public static void main(String[] args) throws Exception {
        BlockingQueue<Integer> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                blockingQueue.put(getSum(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        int result = blockingQueue.take();
        System.out.println("结果为："+result);
    }

    private static int getSum(int num) {
        if ( num < 2) {
            return 1;
        }
        return getSum(num-1) + getSum(num-2);
    }
}

```






