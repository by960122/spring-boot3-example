package demo.thread.without_callback;

import com.example.tool.JsonTools;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description: 无返回结果的回调模型
 */
public class TaskHandler implements TaskCallable<TaskResult> {
    @Override
    public TaskResult callable(TaskResult taskResult) {
        // TODO 拿到结果数据后进一步处理
        System.out.println(JsonTools.toJsonString(taskResult));
        return taskResult;
    }
}
