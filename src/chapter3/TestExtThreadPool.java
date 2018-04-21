package chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO
 * @date 2018/4/21 13:44
 */
public class TestExtThreadPool {
    public static class MyTask implements Runnable{
        public String name;
        public MyTask(String name){
            this.name=name;
        }
        @Override
        public void run() {
            System.out.println("正在执行: Thread ID"+Thread.currentThread().getId()+",Task Name="+name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es=new ThreadPoolExecutor(5,5,0L,
                TimeUnit.MICROSECONDS,new LinkedBlockingDeque<>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行："+((MyTask)r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成："+((MyTask)r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程退出");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask task=new MyTask("TASK-GEYM"+i);
            es.execute(task);
            Thread.sleep(10);

        }

        es.shutdown();//不会立即暴力的中止所，会等待所有任务执行完成后才会关闭有的任务
    }

    /**
     * 笔记
     * 1. 对线程池的自定义增强功能
     * 2.可以对线程池的运行状态进行跟踪，输出一些调试信息帮助系统故障诊断
     */
}
