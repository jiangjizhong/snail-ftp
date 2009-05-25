
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class ChangeWorkDirectoryUpCommand implements Command {
    private FTPController controller;
    public ChangeWorkDirectoryUpCommand(FTPController controller){
        this.controller = controller;
    }

    public void execute() throws IOException, FTPException {
        this.controller.changeWorkDirectoryUp();
    }
    
    
}
