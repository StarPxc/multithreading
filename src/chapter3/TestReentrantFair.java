package chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter2
 * @Description: TODO 公平锁
 * @date 2018/3/19 12:13
 */
public class TestReentrantFair implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {

        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TestReentrantFair r1=new TestReentrantFair();
        Thread t1=new Thread(r1);
        Thread t2=new Thread(r1);
        t1.start();
        t2.start();
    }

    /**
     * 笔记：
     * 1. synchronized是不公平锁
     * 2.公平锁不会产生饥饿现象
     * 3.实现公平锁系统要维护一个有序队列，因此公平锁实现成本比较高，性能低下
     */
}
