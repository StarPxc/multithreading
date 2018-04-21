package chapter3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 锁申请等待限时
 * @date 2018/3/19 9:18
 */
public class TestReentrantLockTimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getId()+"：获得锁");
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getId()+"：get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TestReentrantLockTimeLock timeLock = new TestReentrantLockTimeLock();
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.start();
        t2.start();
        /**
         * 笔记：
         * 1.tryLock接受两个参数，第一个是等待时间，第二个是单位,表示最多等待的时间，超过这个时间就返回false
         * 2.t1占用锁的时间超过了5秒所以t2获取不到锁
         */
    }
}
