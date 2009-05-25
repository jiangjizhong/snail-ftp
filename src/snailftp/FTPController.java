
package snailftp;

import snailftp.queuefile.QueueFile;
import java.io.*;
import java.util.*;
import snailftp.site.*;
/**
 * MVC模式中的控制器，基本上是对Model方法的封装
 * @author jiangjizhong
 */
public interface FTPController {
    /**
     * 登录
     * @param site 站点
     * @throws IOException 如果出现IOException
     * @throws FTPException 如果出现FTP异常
     * @return 如果登录成功，返回true，否则返回false
     */
    void login(Site site)
            throws IOException, FTPException;
    /**
     * 开始传输队列中的文件。
     */
    void transmit() throws IOException, FTPException;
    
    /**
     * 从服务器读取文件列表
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void list() throws IOException, FTPException;
    /**
     * 建文件夹
     * @param dir 文件夹名
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void makeDir(String dir) throws IOException, FTPException;
    
    /**
     * 注销
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void quit() throws IOException, FTPException;
    
    /**
     * 添加所有文件到队列中
     * @param list
     */
    void addQueueFile(List<QueueFile> list);
    /**
     * 添加文件到队列中
     * @param file
     */
    void addQueueFile(QueueFile file);
    /**
     * 改变远程工作目录
     * @param workDirectory
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void changeWorkDirectory(String workDirectory) throws IOException, FTPException ;
    
    /**
     * 到上级目录
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void changeWorkDirectoryUp() throws IOException, FTPException;
    /**
     * 删除文件夹，包括所有的文件
     * @param dir 文件夹名称
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void removeDirectory(String dir) throws IOException, FTPException;
    /**
     * 删除单个文件
     * @param file 文件名
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void removeFile(String file) throws IOException, FTPException;
    /**
     * 终止。可以终止正在执行的操作：比如传输文件
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void abort() throws IOException, FTPException;
    /**
     * 重命名文件或文件夹
     * @param originName 原来的名字
     * @param newName 新名字
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void rename(String originName, String newName) throws IOException, FTPException;
    /**
     * 设定本地工作目录
     * @param localDirectory
     */
    void setLocalDirectory(String localDirectory);
    
    /**
     * 设定IO缓冲区大小。过小的IO缓冲区会使文件传输变慢。不过IO缓冲区太大纯粹是浪费。
     * @param ioBufferSize
     */
    void setIOBufferSize(int ioBufferSize);
    /**
     * 把队列文件移动到队列头部。
     * @param file 要移动的文件
     */
    void moveToHead(QueueFile file);
    /**
     * 把队列文件移动到队列尾部。
     * @param file 要移动的文件
     */
    void moveToTail(QueueFile file);
    /**
     * 删除队列文件
     * @param file 要删除的文件
     */
    void remove(QueueFile file);
    /**
     * 清空队列
     */
    void clearQueue();
    /**
     * 执行命令，这里的命令是FTP原始命令
     * 如：PWD, SYST等。注意，不要执行导致数据传输的命令，如LIST。
     * @param command
     * @throws java.io.IOException
     * @throws snailftp.FTPException
     */
    void executeCommand(String command) throws IOException, FTPException;
    
    
}
