package chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 可重入锁的中断响应
 * @date 2018/3/19 8:44
 */
public class TestReentrantLockInterrupt implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public TestReentrantLockInterrupt(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                /**
                 * 这是一个可以对中断进行响应的锁申请动作，
                 * 即在等待锁的过程中，可以响应中断
                 */
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){ }
                lock2.lockInterruptibly();
            }else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){ }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId()+":线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestReentrantLockInterrupt r1=new TestReentrantLockInterrupt(1);
        TestReentrantLockInterrupt r2=new TestReentrantLockInterrupt(2);
        Thread t1=new Thread(r1);
        Thread t2=new Thread(r2);
        t1.start();t2.start();
        Thread.sleep(1000);
       t2.interrupt();
    }
    /**
     * 笔记：
     *
     * 1.线程t1和t2启动后，t1先占用lock1，再占用lock2；t2先占用lock2，在请求lock1
     * 因此很容易形成t1与t2的相互等待
     * 2.在代码56行主线程main处于休眠，此时两个线程处于死锁状态
     * 3.在代码57行t2线程被中断，故t2会放弃对lock1的申请，同时释放已获得的lock2
     * 这个操作导致t1线程可以顺利的得到lock2而继续执行下去
     * 4.真正完成工作的是t1
     * 5.中断响应可以帮助处理死锁状态，通知线程无需等待
     */
}
