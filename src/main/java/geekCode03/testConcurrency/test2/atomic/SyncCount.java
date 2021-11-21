
package geekCode03.testConcurrency.test2.atomic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncCount {

    private int num = 0;
    /**
     * （1）synchronized是独占锁，加锁和解锁的过程自动进行，易于操作，但不够灵活。
     * ReentrantLock也是独占锁，加锁和解锁的过程需要手动进行，不易操作，但非常灵活。
     * （2）synchronized可重入，因为加锁和解锁自动进行，不必担心最后是否释放锁；
     * ReentrantLock也可重入，但加锁和解锁需要手动进行，且次数需一样，否则其他线程无法获得锁。
     * （3）synchronized不可响应中断，一个线程获取不到锁就一直等着；ReentrantLock可以相应中断。
     */

    private Lock lock = new ReentrantLock(true);

    public int add() {
        try {
            lock.lock();
            return num++;
        } finally {
            lock.unlock();
        }
    }

    public int getNum() {
        return num;
    }
}
