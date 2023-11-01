package demo.executor;

import java.util.Objects;
import java.util.concurrent.*;

import com.example.tool.TimeTools;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2024/2/7
 * @description: 线程池设置超时时间
 */
@Slf4j
public class ExecutorTimeOutDemo {
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        method3();
    }

    private static void method1() {
        // 创建一个新的线程池, 用于执行要限制时间的方法
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建一个新的CompletionService, 用于监控执行时间
        ExecutorCompletionService<Object> completionService = new ExecutorCompletionService<>(executor);
        // 提交要执行的方法
        Future<?> future = completionService.submit(() -> {
            // 这里是要执行的方法
            log.info("execute start.");
            TimeTools.sleep(10000);
            log.info("execute finished.");
            return "execute success.";
        });
        // 获取执行结果
        try {
            log.info("start get result.");
            Future<?> result = completionService.poll(3, TimeUnit.SECONDS);
            if (Objects.isNull(result)) {
                log.warn("execute time out.");
                future.cancel(true);
            } else {
                log.info("execute result: {}", result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.info("execute error: {}", e);
            future.cancel(true);
        }
        log.info("finished.");
    }

    private static void method2() {
        // 创建一个新的CompletableFuture
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            // 这里是要执行的方法
            log.info("execute start.");
            TimeTools.sleep(10000);
            log.info("execute finished.");
            return "execute success.";
        });

        // 获取执行结果
        try {
            log.info("start get result.");
            Object result = future.get(3, TimeUnit.SECONDS);
            log.info("execute result: {}", result);
        } catch (InterruptedException | ExecutionException e) {
            log.info("execute error: {}", e);
            future.cancel(true);
        } catch (TimeoutException e) {
            // 超时了, 结束该方法的执行
            log.warn("execute time out.");
            future.cancel(true);
        }
        log.info("finished.");
    }

    private static void method3() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<?> future = executor.submit(() -> {
            log.info("execute start.");
            TimeTools.sleep(10000);
            log.info("execute finished.");
            return "execute success.";
        });

        ScheduledExecutorService schedulerExecutor = Executors.newSingleThreadScheduledExecutor();
        schedulerExecutor.schedule(() -> {
            if (!future.isDone()) {
                log.warn("execute time out.");
                future.cancel(true);
            }
        }, 3, TimeUnit.SECONDS);
        log.info("finished.");
    }
}
