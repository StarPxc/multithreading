package chapter2;

import chapter3.TestLockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter2
 * @Description: TODO 挂起和继续执行
 * @date 2018/3/24 14:23
 */
public class TestSuspendAndResume {
    public static  Object u=new Object();
    static TestLockSupport.ChangeObjectThread t1=new TestLockSupport.ChangeObjectThread("t1");
    static TestLockSupport.ChangeObjectThread t2=new TestLockSupport.ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in"+getName());
                Thread.currentThread().suspend();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            t1.start();
            Thread.sleep(100);
            t2.start();
            t1.resume();
            t2.resume();
            t1.join();
            t2.join();
            /**
             * 笔记：
             * 1.这两个是已经要被废除的方法
             * 因为这连个方法在导致线程暂停的同时并不会释放锁
             */
        }
    }
}
