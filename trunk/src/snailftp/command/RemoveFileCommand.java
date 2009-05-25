
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class RemoveFileCommand implements Command {

    private FTPController controller;
    private String arg;
    public RemoveFileCommand(FTPController controller, String arg){
        this.controller = controller;
        this.arg = arg;
    }
    
    public void execute() throws IOException, FTPException {
        controller.removeFile(arg);
    }
    
}
