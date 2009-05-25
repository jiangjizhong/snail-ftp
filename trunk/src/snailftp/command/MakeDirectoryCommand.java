
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class MakeDirectoryCommand implements Command {

    private FTPController controller;
    String arg;
    public MakeDirectoryCommand(FTPController controller, String arg){
        this.controller = controller;
        this.arg = arg;
    }
    
    public void execute() throws IOException, FTPException {
        controller.makeDir(arg);
    }
}
