
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class ChangeWorkDirectoryCommand implements Command {

    private FTPController controller;
    private String arg;
    
    public ChangeWorkDirectoryCommand(FTPController controller, String arg){
        this.controller = controller;
        this.arg = arg;
    }

    public void execute() throws IOException, FTPException {
        controller.changeWorkDirectory(arg);
    }
}
