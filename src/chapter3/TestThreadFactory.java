package chapter3;

import java.util.concurrent.*;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 自定义线程创建
 * @date 2018/4/21 9:54
 */
public class TestThreadFactory {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS,
                new SynchronousQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {//使用自定义线程不使用默认的线程工厂
                Thread t = new Thread(r);
                t.setDaemon(true);
                System.out.println("生成线程 " + t);
                return t;
            }
        });
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + " 在忙...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 5; i++) {
            es.submit(r);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            es.shutdown();
        }

    }

    /**
     * 笔记
     * 自定义线程更具灵活性
     *
     */
}
