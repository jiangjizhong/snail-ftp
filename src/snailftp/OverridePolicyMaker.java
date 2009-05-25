
package snailftp;

import snailftp.queuefile.QueueFile;

/**
 * FTPModel在传送文件时，如果文件已经存在，会询问注册的OverridePolicyMaker覆盖策略
 * @author jiangjizhong
 */
public interface OverridePolicyMaker {
    /**
     * 返回覆盖策略
     * @param file
     * @return
     */
    OverridePolicy getOverridePolicy(QueueFile file);
}
