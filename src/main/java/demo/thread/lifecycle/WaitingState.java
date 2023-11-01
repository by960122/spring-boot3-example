package demo.thread.lifecycle;

/**
 * @author: BYDylan
 * @date: 2024/3/13
 * @description:
 */
public class WaitingState implements Runnable {
    @Override
    public void run() {
        while (true) {
            synchronized (WaitingState.class) {
                try {
                    WaitingState.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}