
package snailftp;

/**
 * 消息监视器。FTPModel发生某些事件时，会发布一些消息。
 * 如果对这些消息感兴趣，应该实现这个接口。
 * @author jiangjizhong
 */
public interface MessageObserver {
    /**
     * FTPModel发布的消息
     * @param message
     */
    void update(Message message);
}
