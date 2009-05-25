
package snailftp.command;

import snailftp.*;
import java.io.IOException;
/**
 *
 * @author jiangjizhong
 */
public class RenameCommand implements Command {

    private FTPController controller;
    private String orginName;
    private String newName;
    
    
    public RenameCommand(FTPController controller){
        this.controller = controller;
    }
    
    public void execute() throws IOException, FTPException {
        this.controller.rename(orginName, newName);
    }

    public String getOrginName() {
        return orginName;
    }

    public void setOrginName(String orginName) {
        this.orginName = orginName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

}
