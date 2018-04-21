package chapter2;

/**
 * synchronized关键字的使用

 * @author Ethan

 * @date 2018年3月18日
 */
public class TestSynchronized implements Runnable {
    static TestSynchronized instance=new TestSynchronized();
    static int i=0;

    public synchronized void add() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
			/*synchronized (instance) {
			i++;
			}*/
            add();
        }

    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);//instance必须是一样的，这样才能让两个线程都关注到一个对象锁上去
        Thread t2=new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);

    }
    /**
     * 笔记
     * 1.synchronized的三种用法：
     * 		1）指定加锁对象
     * 		2）直接作用于实例方法
     * 		3）直接作用于静态方法
     * 2.instance必须是一样的，这样才能让两个线程都关注到一个对象锁上去
     * 3.如果把add方法改为静态方法，那么即使对象不是同一个也可以正确同步，因为此时的锁是类锁
     *
     */
}

