package demo.thread.sort;

/**
 * @author: BYDylan
 * @date: 2024/3/13
 * @description: 线程的顺序, Thread.join()方法能够确保线程的执行顺序
 */
public class ThreadSort {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2");
        });
        Thread thread3 = new Thread(() -> {
            System.out.println("thread3");
        });
        thread1.start();
        // 实际上让主线程等待子线程执行完成
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();
    }
}
