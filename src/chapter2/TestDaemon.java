package chapter2;

public class TestDaemon {
    public static class DaemonT extends Thread{
        @Override
        public void run() {
            while(true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t=new DaemonT();
        t.setDaemon(true);//设置为守护线程
        t.start();
        Thread.sleep(2000);


    }
    /**
     * 笔记：
     * 1.守护线程在后台运行，当守护线程要守护的对象已经不存在了，那么整个应用程序就自然的结束了
     * 因此，当一个java应用内，只有守护线程时，java虚拟机就会自然的退出
     * 2.设置守护线程的操作必须是在start()方法之前否则只是被当做用户线程
     * 3.这个例子中只有main是用户线程，当main休眠两秒退出时，整个程序就结束了，但如果不把t设置为守护线程，程序会一直打印下去
     */

}

