package demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.tool.TimeTools;

/**
 * @author: BYDylan
 * @date: 2022/10/18
 * @description:
 */
public class FutureDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(FutureDemo.class);
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int index = 1; index <= 10; index++) {
            int finalIndex = index;
            executor.submit(() -> execTask(finalIndex, atomicInteger));
        }
        LOGGER.info("submit end.");
        while (atomicInteger.get() < 10) {
            TimeTools.sleep(TimeTools.ONE_SEC_MILLIS);
        }
        LOGGER.info("exec end.");
        executor.shutdown();
    }

    private static void execTask(int taskId, AtomicInteger atomicInteger) {
        LOGGER.info("submit task id: {}.", taskId);
        TimeTools.sleep(TimeTools.TEN_SEC_MILLIS);
        atomicInteger.incrementAndGet();
        LOGGER.info("exec task id: {} end.", taskId);
    }
}
