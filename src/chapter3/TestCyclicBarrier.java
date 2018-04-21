package chapter3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 循环栅栏
 * @date 2018/3/20 9:02
 */
public class TestCyclicBarrier {
    public static class Soldier implements Runnable {
        private String solider;
        private final CyclicBarrier cyclic;

        Soldier(String solider, CyclicBarrier cyclic) {
            this.solider = solider;
            this.cyclic = cyclic;
        }

        @Override
        public void run() {
            try {
                //等待所有士兵到齐
                cyclic.await();
                doWorker();
                //等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWorker() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令：【士兵" + N + "个，任务完成】");
            } else {
                System.out.println("司令：【士兵" + N + "个，集合完成】");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N=10;
        Thread[] allSoldier=new Thread[N];
        boolean flag=false;
        CyclicBarrier cyclicBarrier=new CyclicBarrier(N,new BarrierRun(flag,N));
        //设置屏障点，主要为了执行这个方法
        System.out.println("集合队伍");
        for (int i = 0; i <N ; ++i) {
            System.out.println("士兵"+i+"报道！");
            allSoldier[i]=new Thread(new Soldier("士兵"+i,cyclicBarrier));
            allSoldier[i].start();
        }
    }
}
