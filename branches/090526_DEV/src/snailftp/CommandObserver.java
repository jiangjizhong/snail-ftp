
package snailftp;

/**
 * 向服务器发送的ftp命令的监视器。
 * 如果某个类对向服务器发送了哪些命令感兴趣，应该实现这个接口。
 * @author jiangjizhong
 */
public interface CommandObserver {
    /**
     * 向服务器发送命令时调用
     * @param command 想服务器发送的命令
     */
    void update(String command);
}
