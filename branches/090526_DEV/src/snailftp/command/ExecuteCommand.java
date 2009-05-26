
package snailftp.command;

import java.io.IOException;
import snailftp.*;
/**
 *
 * @author jiangjizhong
 */
public class ExecuteCommand implements Command {

    private FTPController controller;
    private String command;
    
    public ExecuteCommand(FTPController controller, String command){
        this.controller = controller;
        this.command = command;
    }
    
    public void execute() throws IOException, FTPException {
        controller.executeCommand(command);
    }
}
