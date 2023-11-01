package demo.thread.without_callback;

/**
 * @author: BYDylan
 * @date: 2024/3/14
 * @description:
 */
public interface TaskCallable<T> {
    T callable(T t);
}
