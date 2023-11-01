package demo.thread.lifecycle;

/**
 * @author: BYDylan
 * @date: 2024/3/13
 * @description:
 */
public class BlockedThread implements Runnable {
    @Override
    public void run() {
        synchronized (BlockedThread.class) {
            while (true) {
                WaitingTime.waitSecond(100);
            }
        }
    }
}
