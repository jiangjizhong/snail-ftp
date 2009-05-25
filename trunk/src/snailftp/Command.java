
package snailftp;

import java.io.IOException;
/**
 * 命令接口，所有的Command都要实现该接口
 * @author jiangjizhong
 */
public interface Command {
    /**
     * 执行
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void execute() throws IOException, FTPException;
}
