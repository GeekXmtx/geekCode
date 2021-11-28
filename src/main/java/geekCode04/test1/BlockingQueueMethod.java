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
