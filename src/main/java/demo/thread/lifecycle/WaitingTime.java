package demo.thread.lifecycle;

import java.util.concurrent.TimeUnit;

/**
 * @author: BYDylan
 * @date: 2024/3/13
 * @description:
 */
public class WaitingTime implements Runnable {
    static void waitSecond(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            waitSecond(200);
        }
    }
}
