
package snailftp;

/**
 * FTP异常
 * @author jiangjizhong
 */
public class FTPException extends Exception {
    public FTPException(){
        super();
    }
    
    public FTPException(String message){
        super(message);
    }
    
    public FTPException(String message, Throwable cause){
        super(message, cause);
    }
    
    public FTPException(Throwable cause){
        super(cause);
    }
}
