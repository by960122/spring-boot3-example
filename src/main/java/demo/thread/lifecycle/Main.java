package demo.thread.lifecycle;

/**
 * @author: BYDylan
 * @date: 2024/3/13
 * @description:
 */
public class Main {
    public static void main(String[] args) {
        new Thread(new WaitingTime(), "WaitingTimeThread").start();
        new Thread(new WaitingState(), "WaitingStateThread").start();
        // BlockedThread-01线程会抢到锁, BlockedThread-02线程会阻塞
        new Thread(new BlockedThread(), "BlockedThread-01").start();
        new Thread(new BlockedThread(), "BlockedThread-02").start();
    }
}
