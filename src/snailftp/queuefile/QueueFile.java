
package snailftp.queuefile;

import snailftp.*;

/**
 * 不可变QueueFile，表示一个待传送的队列文件
 * @author jiangjizhong
 */
public interface QueueFile {
    final static String DIRECTORY_UPLOAD_ICON_FILENAME = "/snailftp/resource/dirup.jpg";
    final static String FILE_UPLOAD_ICON_FILENAME = "/snailftp/resource/fileup.jpg";
    final static String DIRECTORY_DOWNLOAD_ICON_FILENAME = "/snailftp/resource/dirdown.jpg";
    final static String FILE_DOWNLOAD_ICON_FILENAME = "/snailftp/resource/filedown.jpg";
    long getSize();
    String getIconFilename();
    String getSourceDirectory();
    String getDestinationDirectory();
    String getName();
    String getFrom();
    String getTo();
    /**
     * 传送，不同的队列文件有不同的传送方式。
     * 有的要上传，有的要下载。有的是文件有的是文件夹。
     * 不过QueueFile并不需要自己实现传输的代码。FTPModel里已经提供了足够的支持。
     * QueueFile基本上只要调用对应的方法就行了
     * @param ftpModel FTPModel
     * @param queue 自己所在的文件队列
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void transmit(FTPModel ftpModel, FileQueue queue) throws java.io.IOException, FTPException;
}
