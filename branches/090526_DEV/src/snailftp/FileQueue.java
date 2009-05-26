
package snailftp;

import snailftp.queuefile.QueueFile;
import java.util.*;
/**
 *
 * @author jiangjizhong
 */
public interface FileQueue {
    /**
     * 添加文件到队列中
     * @param file
     */
    void add(QueueFile file);
    
    Iterator<QueueFile> iterator();
    /**
     * 删除队列文件
     * @param file 要删除的文件
     */
    void remove(QueueFile file);
    /**
     * 把队列文件移动到队首
     * @param file
     */
    void moveToHead(QueueFile file);
    /**
     * 把队列文件移动到队尾
     * @param file
     */
    void moveToTail(QueueFile file);
    /**
     * 清空队列文件
     */
    void clear();
    void addObserver(FileQueueObserver observer);
    void removeObserver(FileQueueObserver observer);
    /**
     * 获取并删除第一个队列文件
     * @return 第一个队列文件。如果队列为空则返回null
     */
    QueueFile getFirst();
    /**
     * 添加队列文件到队列头部
     * @param file
     */
    void addFirst(QueueFile file);
}
