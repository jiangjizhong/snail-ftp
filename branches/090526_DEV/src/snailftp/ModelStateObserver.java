
package snailftp;

/**
 * FTPModel的状态监视器。
 * @author jiangjizhong
 */
public interface ModelStateObserver {
    /**
     * 状态改变了
     * @param s
     */
    void update(FTPModel.State s);
}
