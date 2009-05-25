
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class QuitCommand implements Command {

    private FTPController controller;
    
    public QuitCommand(FTPController controller){
        this.controller = controller;
    }
    
    public void execute() throws IOException, FTPException {
        controller.quit();
    }

}
