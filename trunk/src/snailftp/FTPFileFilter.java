
package snailftp;

/**
 * ftp文件过滤器，在FTPFileParser中要使用这个接口的一个实现，以决定哪些文件需要过滤掉。
 * @author jiangjizhong
 */
public interface FTPFileFilter {
    /**
     * 是否通过指定的文件
     * @param file
     * @return true 通过。
     * @return false 不通过
     */
    boolean accept(FTPFile file);
}
