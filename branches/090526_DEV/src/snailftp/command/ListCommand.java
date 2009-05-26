
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class ListCommand implements Command {

    private FTPController controller;
    
    public ListCommand(FTPController controller){
        this.controller = controller;
    }
    
    public void execute() throws IOException, FTPException {
        this.controller.list();
    }

}
