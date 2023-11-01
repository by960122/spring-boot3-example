package demo.thread.without_callback;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description:
 */
@Data
public class TaskResult implements Serializable {
    private static final long serialVersionUID = 8678277072402730062L;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 任务消息
     */
    private String taskMessage;
    /**
     * 任务结果数据
     */
    private String taskResult;
}
