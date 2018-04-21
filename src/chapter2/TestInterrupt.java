package chapter2;

public class TestInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread() {
            @Override
            public void run() {
                while(true) {
                    System.out.println("我在运行");
                    if(Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                        System.out.println("Interrupted When Sleep");
                        /**
                         * Thread.sleep()方法由于中断而抛出异常，此时，它会清除中断标记，如果不加处理，那么在下一次循环开始时
                         * ，就无法捕捉这个中断，故在异常处理中，再次设置中断标志位
                         */
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
        /**
         * 笔记：
         * 1.一个线程进入阻塞状态可能的原因：
         * ①通过调用sleep(millseconds)使任务进入休眠状态；
         * ②通过调用wait（）使线程挂起，直到线程获取notify（）/notifyAll（）消息
         * ③任务在等待某个输入 / 输出流的完成；
         * ④任务试图在某个对象上调用其同步控制方法，但是对象锁不可用，因为另一个任务已经获取了该锁；
         *
         * 2.Thread类包含interrupt（）方法，用于终止阻塞任务；
         * 1）中断①②类线程休眠，挂起阻塞的方法
         * 2）中断③类I/O阻塞的方法
         * 3）中断④类被互斥阻塞的线程
         *
         * 3.调用interrupt方法不会立即结束线程仅仅只是设置了个中断标识位
         * 需要使用在sleep中catch异常停止，在异常捕获中加return可以直接结束当前线程
         */
    }

}

