package chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter2
 * @Description: TODO 测试可重入锁
 * @date 2018/3/19 8:17
 */
public class TestReentrantLock implements Runnable{
    public static ReentrantLock lock=new ReentrantLock();
    public static int i=0;

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            lock.lock();
            lock.lock();//这种写法也是可以的，因为是重入锁，但是获得多次锁，一定要释放相同数量的锁
            try {
                i++;
            }finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestReentrantLock tr=new TestReentrantLock();
        Thread t1=new Thread(tr);
        Thread t2=new Thread(tr);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
        /**
         * 笔记
         * 1.重入锁有着显示操作过程，必须手动指定开，关锁的时机，
         * 所以，重入锁对逻辑控制的灵活性远远大于synchronized
         * 2.释放锁的操作必须在finally中
         */
    }
}
