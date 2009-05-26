
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class TransmitCommand implements Command {

    FTPController controller;
    
    public TransmitCommand(FTPController controller){
        this.controller = controller;
    }
    
    public void execute() throws IOException, FTPException {
        controller.transmit();
    }

}
