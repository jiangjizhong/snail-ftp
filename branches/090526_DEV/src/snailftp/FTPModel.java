
package snailftp;

import snailftp.queuefile.QueueFile;
import java.util.*;
import java.io.*;
/**
 * FTP客户端MVC结构中的Model，提供ftp大部分操作。
 * @author jiangjizhong
 */

public interface FTPModel {
    /**
     * 默认FTP服务器端口
     */
    static final int DEFAULT_PORT = 21;
    /**
     * 默认IO缓冲区大小：20K
     */
    static final int DEFAULT_IO_BUFFER_SIZE = 1024 * 20;
    /**
     * 匿名登录时的用户名
     */
    static final String ANONYMOUS_USERNAME = "anonymous";
    /**
     * 匿名登录时的密码
     */
    static final String ANONYMOUS_PASSWORD = "anonymous";
    
    static final String COMMAND_QUIT = "QUIT";
    static final String COMMAND_USER = "USER";
    static final String COMMAND_PASS = "PASS";
    static final String COMMAND_LIST = "LIST";
    static final String COMMAND_CWD = "CWD";
    static final String COMMAND_PWD = "PWD";
    static final String COMMAND_MKD = "MKD";
    static final String COMMAND_TYPE = "TYPE";
    static final String COMMAND_RETR = "RETR";
    static final String COMMAND_STOR = "STOR";
    static final String COMMAND_CDUP = "CDUP";
    static final String COMMAND_RMD = "RMD";
    static final String COMMAND_DELE = "DELE";
    static final String COMMAND_RNFR = "RNFR";
    static final String COMMAND_RNTO = "RNTO";
    static final String COMMAND_ABOR = "ABOR";
    static final String COMMAND_PASV = "PASV";
    
    static enum State{
        UNCONNECTION, TRANSMIT, IDLE
    }
    
    /**
     * 是否使用被动模式。
     * 应该连接服务器之前设定此选项。
     * @param b
     */
    void setPassiveMode(boolean b);
    
    /**
     * 登录ftp服务器
     * @param host 服务器主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return 成功登录返回true，失败返回false
     */
    void open(String host, int port, String username, String password) throws FTPException;
    
    /**
     * 获取当前服务器响应信息
     * @return
     */
    Reply getReply();
    
    /**
     * 开始传输队列里的文件
     * @param Queue 传输队列
     */
    void transmit(FileQueue queue) throws IOException, FTPException;
    
    /**
     * 获取当前目录下服务器文件列表
     * @return
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    List<FTPFile> getFileList() throws IOException, FTPException;
    
    /**
     * 设定工作目录
     * @param dir
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void setWorkDirectory(String dir) throws IOException, FTPException;
    void uploadFile(QueueFile file) throws IOException, FTPException;
    void downloadFile(QueueFile file) throws IOException, FTPException;
    
    /**
     * 进入上级目录
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void changeWorkDirectoryUp() throws IOException, FTPException;
    
    /**
     * 获取当前工作目录
     * @return
     */
    String getWorkDirectory();
    
    /**
     * 执行LIST命令，从服务器获取FTP文件列表
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void list() throws IOException, FTPException;
    
    /**
     * 建立文件夹，不自动刷新文件列表
     * @param dir
     * @throws java.io.IOException
     * @throws jftp.FTPException
     */
    void makeDir(String dir) throws IOException, FTPException;
    
    /**
     * 设定本地工作目录
     * @param dir
     */
    void setLocalDirectory(String dir);
    
    /**
     * 执行QUIT命令，从服务器注销
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void quit() throws IOException, FTPException;
    
    /**
     * 删除文件夹（包括里面的文件和子文件夹），不自动刷新文件列表
     * @param dir
     * @throws java.io.IOException
     * @throws jftp.FTPException
     */
    void removeDirectory(String dir) throws IOException, FTPException;
    /**
     * 删除文件，不自动刷新文件列表
     * @param file
     * @throws java.io.IOException
     * @throws jftp.FTPException
     */
    void removeFile(String file) throws IOException, FTPException;
    /**
     * 执行ABORT命令，强制中断
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void abort() throws IOException, FTPException;
    /**
     * 重命名，不自动刷新文件列表
     * @param originName
     * @param newName
     * @throws java.io.IOException
     * @throws jftp.FTPException
     */
    void rename(String originName, String newName) throws IOException, FTPException;
    
    void addReplyObserver(ReplyObserver observer);
    void removeReplyObserver(ReplyObserver observer);
    
    void addCommandObserver(CommandObserver observer);
    void removeCommandObserver(CommandObserver observer);
    
    void addFileListObserver(FTPFileListObserver observer);
    void removeFileListObserver(FTPFileListObserver observer);
    
    void addCurrentDirectoryObserver(CurrentDirectoryObserver observer);
    void removeCurrentDirectoryObserver(CurrentDirectoryObserver observer);
    
    void addFileTransmitObserver(FileTransmitObserver observer);
    void remvoeFileTransmitObserver(FileTransmitObserver observer);
    void addMessageObserver(MessageObserver observer);
    void removeMessageObserver(MessageObserver observer);
    
    String getLocalDirectory();
    
    State getState();
    
    void addModelStateObserver(ModelStateObserver observer);
    void removeModelStateObserver(ModelStateObserver observer);
    
    void setOverridePolicyMaker(OverridePolicyMaker maker);
    
    int getPort();
    String getHost();
    String getUsername();
    String getPassword();
    
    void setIOBufferSize(int size);
    int getIOBufferSize();
    
    String getCurrentFilename();
    long getTransferedSize();
    long getTotalSize();
    long getTimePassed();
    void executeCommand(String command) throws IOException, FTPException;
}
