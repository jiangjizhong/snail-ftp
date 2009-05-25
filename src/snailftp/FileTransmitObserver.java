
package snailftp;

/**
 * 文件传送监视器
 * @author jiangjizhong
 */
public interface FileTransmitObserver {
    /**
     * 开始传送时调用
     */
    void beginTransmit();
    /**
     * 传送完成时调用
     */
    void endTransmit();
}
