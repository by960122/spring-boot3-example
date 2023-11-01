package demo.thread;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description: DateTimeFormatter类是线程安全的, 可以在高并发场景下直接使用DateTimeFormatter类来处理日期的格式化操作 备注: joda-time是第三方处理日期时间格式化的类库,
 *               是线程安全的
 */
public class SimpleDateFormatTest07 {
    // 执行总次数
    private static final int EXECUTE_COUNT = 1000;
    // 同时运行的线程数量
    private static final int THREAD_COUNT = 20;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        LocalDate parse = LocalDate.parse("2020-01-01", formatter);
                    } catch (Exception e) {
                        System.out.println("线程：" + Thread.currentThread().getName() + " 格式化日期失败");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("所有线程格式化日期成功");
    }
}
