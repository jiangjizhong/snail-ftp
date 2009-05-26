
package snailftp;

/**
 *
 * @author jiangjizhong
 */
public class DefaultFTPFileFilter implements FTPFileFilter {

    public boolean accept(FTPFile file) {
        if(file.isDirectory()){
            String name = file.getName();
            if(name.contains(".") || name.contains("..")){
                return false;
            }
        }
        return true;
    }

}
