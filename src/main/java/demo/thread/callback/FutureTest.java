package demo.thread.callback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description: 有返回结果的异步模型
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask<>(() -> "测试FutureTask获取异步结果");
        executorService.execute(futureTask);
        System.out.println(futureTask.get());
        executorService.shutdown();
    }
}
