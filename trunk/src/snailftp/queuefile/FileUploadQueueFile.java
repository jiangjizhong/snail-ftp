
package snailftp.queuefile;

import snailftp.*;
import java.io.*;
/**
 *
 * @author jiangjizhong
 */
public class FileUploadQueueFile extends AbstractQueueFile {

    public FileUploadQueueFile(String name, String srcDir, String destDir, long size){
        super(name, srcDir, destDir, size);
    }
    
    @Override
    protected String getFromSeparator() {
        return File.separator;
    }

    @Override
    protected String getToSeparator() {
        return "/";
    }

    public String getIconFilename() {
        return QueueFile.FILE_UPLOAD_ICON_FILENAME;
    }

    public void transmit(FTPModel ftpModel, FileQueue queue) throws IOException, FTPException {
        if (!ftpModel.getLocalDirectory().equals(this.sourceDirectory)) {
            ftpModel.setLocalDirectory(this.sourceDirectory);
        }
        if (!ftpModel.getWorkDirectory().equals(this.destinationDirectory)) {
            ftpModel.setWorkDirectory(this.destinationDirectory);
        }
        ftpModel.uploadFile(this);
    }
}