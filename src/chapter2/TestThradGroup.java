package chapter2;

/**
 * 创建线程组


 * @author Ethan

 * @date 2018年3月18日
 */
public class TestThradGroup implements Runnable {



    @Override
    public void run() {
        String groupAndName=Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName();
        while(true) {
            System.out.println("I am"+groupAndName);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        ThreadGroup tg=new ThreadGroup("PringGroup");
        Thread t1=new Thread(tg,new TestThradGroup(),"T1");
        Thread t2=new Thread(tg,new TestThradGroup(),"T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();

    }

}

