package chapter4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter4
 * @Description: TODO 线程安全的第二种思路---增加资源
 * @date 2018/4/22 12:16
 */
public class TestThreadLocal {
    private static ThreadLocal<SimpleDateFormat>  t1=new ThreadLocal<SimpleDateFormat>();
    public static class ParseDate implements Runnable{
        int i=0;
        public  ParseDate(int i){
            this.i=i;
        }
        @Override
        public void run() {
            try {
                if(t1.get()==null){
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t=t1.get().parse("2015-03-09 19:29:"+i%60);
                System.out.println(i+":"+t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            es.execute(new ParseDate(i));
        }


        /**
         * 笔记：
         * ThreadLocal是线程的局部变量，只有当前线程可以访问
         * 为每一个线程人手分配一个对象的工作并不是由ThreadLocal来完成的，而是需要在应用层面保证的。
         * 如果在应用上为每一个线程分配了相同的对象实例，那么ThreadLocal也不能保证线程安全。
         * ThreadLocal底层是一个ThreadLocalMap，这是一个弱引用，JVM在垃圾回收时，如果发现弱引用，就会立即回收
         * 如果你希望及时回收放在ThreadLocal中的对象，最好使用ThreadLocal.remove()方法，因为当对象太大且当前线程
         * 放在线程池中的时候可能会造成内存泄漏。
         */
    }
}
