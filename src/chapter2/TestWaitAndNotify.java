package chapter2;


public class TestWaitAndNotify {

    final static Object object=new Object();
    public static class T1 extends Thread{
        @Override
        public void run() {
            synchronized (object) {//申请object的对象锁
                System.out.println(System.currentTimeMillis()+":T1 Start!");
                try {
                    System.out.println(System.currentTimeMillis()+":T1 wait for object!");
                    object.wait();//进行等待并且释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //T1在获得notify通知时也会去先获取对象锁
            System.out.println(System.currentTimeMillis()+":T1 end!");

        }
    }
    public static class T2 extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis()+":T2 Start! notify one thread");
                object.notify();//先去申请对象锁
                System.out.println(System.currentTimeMillis()+":Te end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static void main(String[] args) {
        Thread t1=new T1();
        Thread t2=new T2();
        t1.start();
        t2.start();

    }

    /**
     * 笔记：
     * 1.wait()和sleep()都能让线程等待若干时间
     * 2.wait()可以被唤醒
     * 3.wait()方法会释放目标对象的锁，sleep()不会释放任何资源
     * 4.wait()和notify()是object的方法，任何对象都可以使用
     * 5.wait()和notify()会先去获取对象锁在进行操作，方法结束后会释放对象锁
     * 6.wait()，notify()和两个废弃的方法suspend()，resume()方法类似，这两个方法不会释放锁资源
     * 7.当调用wait()方法后当前线程就会在这个对象上等待s
     * 8.sleep()和wait()方法的区别：
     *   1）sleep() 方法是线程类（Thread）的静态方法，让调用线程进入睡眠状态，让出执行机会给其他线程，
     *   等到休眠时间结束后，线程进入就绪状态和其他线程一起竞争cpu的执行时间。因为sleep() 是static静态的方法
     *   ，他不能改变对象的机锁，当一个synchronized块中调用了sleep() 方法，线程虽然进入休眠，
     *   但是对象的机锁没有被释放，其他线程依然无法访问这个对象。
     *   2）wait()是Object类的方法，当一个线程执行到wait方法时，它就进入到一个和该对象相关的等待池，
     *   同时释放对象的机锁，使得其他线程能够访问，可以通过notify，notifyAll方法来唤醒等待的线程
     *   3）sleep必须捕获异常，而wait，notify和notifyAll不需要捕获异常
     */

}

