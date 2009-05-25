
package snailftp;

/**
 *
 * @author jiangjizhong
 */
public interface CurrentDirectoryObserver {
    /**
     * 目录发生了变化
     * 注意，如果某个参数为null，表示这个参数没有发生变化
     * @param localDirectory
     * @param remoteDirectory
     */
    void update(String localDirectory, String remoteDirectory);
}
