package demo.thread.without_callback;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description:
 */
public class TaskExecutor implements Runnable {
    private TaskCallable<TaskResult> taskCallable;
    private String taskParameter;

    public TaskExecutor(TaskCallable<TaskResult> taskCallable, String taskParameter) {
        this.taskCallable = taskCallable;
        this.taskParameter = taskParameter;
    }

    @Override
    public void run() {
        // TODO 一系列业务逻辑,将结果数据封装成TaskResult对象并返回
        TaskResult result = new TaskResult();
        result.setTaskStatus(1);
        result.setTaskMessage(this.taskParameter);
        result.setTaskResult("异步回调成功");
        taskCallable.callable(result);
    }
}
