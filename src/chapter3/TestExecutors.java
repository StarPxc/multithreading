package chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 线程池
 * @date 2018/3/24 15:02
 */
public class TestExecutors {
    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+":Thread ID:"+Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        MyTask task=new MyTask();
        ExecutorService es= Executors.newCachedThreadPool();
        for (int i = 0; i <10 ; i++) {
            es.submit(task);

        }
        /**
         * 笔记：
         * 1.newFixedThreadPool(int nThreads):该方法返回一个固定线程数量的线程池。
         * 2.newSingleThreadExecutor():该方法返回只有一个线程的线程池
         * 3.newCachedThreadPool():改方法返回一个可根据实际情况调整线程数量的线程池
         * 4.newSingleThreadScheduledExecutor()：该对象返回一个ScheduleExecutorService对象，线程池的大小为1
         * ScheduleExecutorService接口在ExecutorService接口上扩展了在给定时间执行某任务的功能。
         * 5.newScheduledThreadPool()方法：改方法也返回一个ScheduledExecutorService()对象，但该线程池可以指定线程数量
         *
         *
         * 6.123几种实现方式其实都只是ThreadPoolExecutor类的封装，因为这个类有个很重要的构造函数
         * ●corePoolSize：指定了线程池中的线程数量。
         * ●maximumPoolSize：指定了线程池中的最大线程数量
         * ●keepAliveTime：当前线程池数量超过corePoolSize时，多余的  空闲线程的存活时间
         * ●unit：keepAliveTime的单位
         * ●workQueue：任务队列，被提交但尚未被执行的任务
         * ●threadFactory：线程工程，用于创建线程，一般用默认的即可
         * ●handler:拒绝策略。当任务太多来不及处理时，如何拒绝任务
         *
         * workQueue分类:
         * ▶直接提交的队列：
         * 该功能由SynchronizedQueue提供，SynchronizedQueue对象的一个特殊的BlockingQueue，SynchronizedQueue没有容量
         * 每一个插入操作都要等待一个相应的删除操作，反之，每一个删除操作都要等待对应的插入操作。如果使用SynchronizedQueue，
         * 提交的任务不会被真实保存，而总是将新任务提交给线程执行，如果没有空闲的进程，则尝试创建新的进程，如果进程数量
         * 达到上限，则执行拒绝策略，所以，使用SynchronizedQueue队列，通常要设置很大的maximumPoolSize值，否则很容易执行拒绝策略
         *
         * ▶有界的任务队列：
         * 有界的任务队列可以使用ArrayBlockingQueue实现，构造函数如下public ArrayBlockingQueue(int capacity)，当使用有界
         * 任务队列时，若有新的任务需要执行，如果线程池的实际线程数小于corePoolSize，则会优先创建新的线程，若大于corePoolSize，
         * 则会将新任务加入等待队列。若等待队列已满，无法加入，则在总线程数不大于maximumPoolSize的前提下，创建新的进程任务。
         * 若大于maximumPoolSize，执行拒绝策略，可见，有界队列仅当在任务队列装满时，才可能将线程数提升到corePoolSize以上，
         * 换言之，除非系统繁忙，否则确保核心线程数维持在corePoolSize。
         * ▶ 无界任务队列
         * 无界任务队列可以通过LinkedBlockingQueue来实现，与有界任务队列相比，除非系统资源耗尽，否则不存在任务入队失败
         * 的情况。也就是说它的任务队列是无界的，当线程数满足corePoolSize时，若有新的任务增加，责任务将直接进入队列等待，
         * 任务队列会不会满，会一直扩张直到耗尽系统资源
         * ▶优先任务队列
         * 带有优先级的队列，通过PriorityBlockQueue实现它是一个特殊的无界队列
         *
         *
         * JDK内置拒绝策略
         * ●AbortPolicy 该策略直接抛出异常，阻止系统正常工作
         * ●CallerRunsPolicy 只要线程池未关闭，该策略直接在调用者桟中，运行当前被丢弃的任务，这样做不会正真丢弃任务
         * 但是任务提交线程的性能可能会急剧下降
         * ●DiscardOledestPolicy 丢弃最老的一个请求
         * ●DiscardPolicy 默默丢弃无法处理的任务，如果运行任务丢失这个是最好的策略
         * ●自定义策略 实现RejectedExecutionHandler
         */
    }

}
