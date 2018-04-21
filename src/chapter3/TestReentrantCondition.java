package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 重入锁的条件
 * @date 2018/3/19 12:13
 */
public class TestReentrantCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock(true);
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                condition.await();//相当于wait()
                System.out.println("Thread is going on");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestReentrantCondition r1 = new TestReentrantCondition();
        Thread t1 = new Thread(r1);
        t1.start();
        Thread.sleep(2000);
        //通知t1继续执行
        lock.lock();//当前线程先要去获得锁
        condition.signal();//相当一notify()
        lock.unlock();//必须要释放锁
        /**
         * 笔记：
         * 1.await()方法和wait方法一样要持有相关的重入锁
         */

    }

    /**
     * 笔记：
     * 1. synchronized是不公平锁
     * 2.公平锁不会产生饥饿现象
     * 3.实现公平锁系统要维护一个有序队列，因此公平锁实现成本比较高，性能低下
     */
}
