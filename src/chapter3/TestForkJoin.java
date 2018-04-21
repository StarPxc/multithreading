package chapter3;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 分而治之  暂时没看懂姑且先把代码放上去
 * @date 2018/4/21 15:59
 */
public class TestForkJoin extends RecursiveTask<Long>{
    private final int THERSHOLD=10000;
    private long start;
    private long end;
    private TestForkJoin(long start, long end){
        this.start=start;
        this.end=end;

    }
    @Override
    protected Long compute() {
       long sum=0;
       boolean canCompute=(end-start)<THERSHOLD;
       if (canCompute){
           for (long i=start;i<end;i++){
               sum+=i;

           }
       }else {
           //分成100个小任务
           long step=(start+end)/100;
           ArrayList<TestForkJoin> subTasks=new ArrayList<>();
           long pos=start;
           for (int i = 0; i <100 ; i++) {
               long lastOne=pos+step;
               if(lastOne>end){
                   lastOne=end;
               }
               TestForkJoin subTask=new TestForkJoin(pos,lastOne);
               pos+=step+1;
               subTasks.add(subTask);
               subTask.fork();
           }
           for (TestForkJoin t:subTasks){
               sum+=t.join();
           }
       }
       return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        TestForkJoin task=new TestForkJoin(0,200000L);
        ForkJoinTask<Long> result=forkJoinPool.submit(task);
        try {
            long res=result.get();
            System.out.println("sum="+res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
