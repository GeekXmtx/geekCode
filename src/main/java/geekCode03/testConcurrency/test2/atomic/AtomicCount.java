
package geekCode03.testConcurrency.test2.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCount {

    private AtomicInteger num = new AtomicInteger();

    public int add() {
        return num.getAndIncrement();
    }

    public int getNum() {
        return num.get();
    }
}
