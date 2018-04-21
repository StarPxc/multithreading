package chapter2;

/**
 * 等待线程结束（join）和谦让（yield）


 * @author Ethan

 * @date 2018年3月18日
 */
public class TestJoinAndYield {

    public volatile static int i=0;
    public static class AddThread extends Thread{
        @Override
        public void run() {
            for (i = 0; i < 100000000; i++) ;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        AddThread at=new AddThread();
        at.start();
        at.join();
        System.out.println(i);
    }
    /**
     * 笔记
     * 1.如果不使用join（）方法输出的i可能是很小的值，因为AddThread还没开始执行完毕i的值就已经输出了
     * 2.join的本质是让调用线程wait()在当前线程的对象实例上
     * 3.不要在Thread对象实例上使用wait()和notify()，因为很有可能会影响系统API的工作，或者被系统API影响
     * 4.Thread.yield()一旦执行会让出CPU，继续进行CPU资源的争夺，如果一个线程不是那么重要或者优先级非常低
     * 而且害怕它占用太多的资源就可以在适当时候调用这个方法
     */
}

