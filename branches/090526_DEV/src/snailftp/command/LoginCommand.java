
package snailftp.command;

import snailftp.*;
import snailftp.site.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class LoginCommand implements Command {
    private FTPController controller;
    private Site site;
    
    public LoginCommand(FTPController controller, Site site){
        this.site = site;
        this.controller = controller;
    }
    
    public void execute() throws IOException, FTPException {
        this.controller.login(site);
    }
}
