
package snailftp;

/**
 * 文件队列监视器
 * @author jiangjizhong
 */
public interface FileQueueObserver {
    /**
     * 队列文件发生了变化时调用
     * @param queue
     */
    void update(FileQueue queue);
}
