package chapter3;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 读写锁
 * @date 2018/3/19 13:27
 */
public class TestReadWriteLock {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private static Lock writeLook = reentrantReadWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();//模拟读操作
            Thread.sleep(1000);//读操作的耗时越多，读写锁优势就越明显
            System.out.println("读");
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("写");
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final TestReadWriteLock demo = new TestReadWriteLock();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(lock);
                    //demo.handleRead(readLock);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleWrite(lock,new Random().nextInt());
                    //demo.handleWrite(writeLook, new Random().nextInt());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(writeRunnable).start();
        }


    }



    /**
     * 笔记:
     * 1.读写锁使得读与读之间可以并行进行
     * 2.在一个系统中，读操作的次数远远大于写操作，则读写锁就可以发挥最大的功效
     */
}
