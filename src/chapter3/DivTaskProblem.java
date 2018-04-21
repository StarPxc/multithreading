package chapter3;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 被吃掉的异常信息
 * @date 2018/4/21 14:05
 */
public class DivTaskProblem implements Runnable{
    int a,b;
    public DivTaskProblem(int a,int b){
        this.a=a;
        this.b=b;
    }
    @Override
    public void run() {
    double re=a/b;
        System.out.println(re);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pools=new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i = 0; i <5 ; i++) {
            //pools.submit(new DivTaskProblem(100,i));//只会输出4个结果，分母为0的异常会被“吃掉”
            pools.execute(new DivTaskProblem(100,i));//使用execute会打印出异常堆栈信息
        }
    }
}
