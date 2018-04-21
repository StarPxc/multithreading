package chapter3;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 线程阻塞工具类
 * @date 2018/3/24 14:14
 */
public class TestLockSupport {
    static final Object u=new Object();
    static ChangeObjectThread t1=new ChangeObjectThread("t1");
    static ChangeObjectThread t2=new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in"+getName());
                LockSupport.park();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            t1.start();
            Thread.sleep(100);
            t2.start();
            LockSupport.unpark(t1);
            LockSupport.unpark(t2);
            t1.join();
            t2.join();
            /**
             * 笔记：
             * 1.park()方法不需要获得对象的锁也不会抛出InterruptException
             * 2.使用了信号量机，它为每一个线程准备了一个许可，如果许可可用
             * 那么park()函数立即返回，并且消费这个许可（也就是将可用变为不可用），
             * 如果许可不可用，就会阻塞。而unpark()则使许可变为可用，许可不能累加，永远只有一个
             * 特点：即使unpark()执行在park()之前，它也可以使下一次park()操作立即返回。
             * park()能响应中断但不会抛出中断异常，它只会默默的返回
             */
        }
    }

}
