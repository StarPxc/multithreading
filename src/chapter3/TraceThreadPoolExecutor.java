package chapter3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ethanp
 * @version V1.0
 * @Package chapter3
 * @Description: TODO 让异常堆栈信息更详细
 * @date 2018/4/21 14:41
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command,clientTrace(),Thread.currentThread().getName()));
    }

    private Runnable wrap(final Runnable command, final Exception clientStack, String currentThreadName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                }catch (Exception e){
                    clientStack.printStackTrace();;
                    throw  e;
                }
            }
        };
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }
}
