package snailftp;

import java.net.*;
import java.util.Enumeration;

/**
 *
 * @author jiangjizhong
 */
public class App {

    public static void main(String[] args) {
        FTPModel model = new DefaultFTPModel();
        FTPController controller = new DefaultFTPController(model);
    }
}
