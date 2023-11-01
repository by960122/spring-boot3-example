package demo.thread.without_callback;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description:
 */
public class TaskCallableTest {
    public static void main(String[] args) {
        TaskCallable<TaskResult> taskCallable = new TaskHandler();
        TaskExecutor taskExecutor = new TaskExecutor(taskCallable, "测试回调任务");
        new Thread(taskExecutor).start();
    }
}
