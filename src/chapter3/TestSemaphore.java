package chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 信号量
 * @date 2018/3/19 13:04
 */
public class TestSemaphore implements Runnable {
    private final Semaphore semp=new Semaphore(5);
    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+"：done！");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec= Executors.newFixedThreadPool(20);
        final TestSemaphore demo=new TestSemaphore();
        for (int i = 0; i <20 ; i++) {
            exec.submit(demo);
        }
    }
    /**
     * 笔记：
     * 1.Semaphore是对锁的扩展，无论是synchronized还是ReentrantLock每次都只能允许一个线程访问
     * Semaphore允许多个线程访问一个资源
     */
}
