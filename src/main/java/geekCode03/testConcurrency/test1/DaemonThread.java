package geekCode03.testConcurrency.test1;

public class DaemonThread {
    
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
                try {
                    Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                Thread t = Thread.currentThread();
                System.out.println("当前线程:" + t.getName());
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        //开启守护线程 JVM虚拟机会把当前线程终止
        thread.setDaemon(true);
        thread.start();

        //Thread.sleep(5500);
    }
    
    
}
