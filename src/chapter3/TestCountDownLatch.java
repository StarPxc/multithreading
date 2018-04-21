package chapter3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 倒计时器
 * @date 2018/3/19 16:40
 */
public class TestCountDownLatch implements Runnable{
    static final CountDownLatch end=new CountDownLatch(10);
    static final TestCountDownLatch demo=new TestCountDownLatch();

    @Override
    public void run() {
        try {
            //模拟检查任务
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check rocket");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newFixedThreadPool(10);
        for (int i = 0; i <10 ; i++) {
            exec.submit(demo);
        }
        //等待检查
        end.await();
        System.out.println("Fire!");
        exec.shutdown();
    }
    /**
     * 笔记：
     * 1.这个类可以让某个线程等待其他线程的结束
     */
}
