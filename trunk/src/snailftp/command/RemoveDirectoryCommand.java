
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class RemoveDirectoryCommand implements Command {

    private FTPController controller;
    private String arg;
    public RemoveDirectoryCommand(FTPController controller, String arg){
        this.controller = controller;
        this.arg = arg;
    }
    
    public void execute() throws IOException, FTPException {
        controller.removeDirectory(arg);
    }

    
}
