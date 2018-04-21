package chapter3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 定时任务调度
 * @date 2018/3/24 15:23
 */
public class TestScheduleExecutorService {
    public static void main(String[] args) {
        ScheduledExecutorService ses= Executors.newScheduledThreadPool(10);
        //如果前面的任务没有完成，则调度不会启动
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis()/1000+" "+Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0,2, TimeUnit.SECONDS);
        /**
         * 笔记：
         * 1.上述代码表示，每个两秒执行一次调度，执行时间是1秒
         * 2.如果任务遇到异常，那么后续的所有子任务都会停止调度，应此，必须保证异常被及时处理。
         */
    }
}
