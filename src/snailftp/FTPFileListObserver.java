
package snailftp;

import java.util.List;
/**
 *
 * @author jiangjizhong
 */
public interface FTPFileListObserver {
    void update(List<FTPFile> fileList);
}
