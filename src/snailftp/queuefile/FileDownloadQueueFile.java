
package snailftp.queuefile;

import snailftp.*;
import java.io.*;

/**
 *
 * @author jiangjizhong
 */
public class FileDownloadQueueFile extends AbstractQueueFile {

    public FileDownloadQueueFile(String name, String srcDir, String destDir, long size){
        super(name, srcDir, destDir, size);
    }
    
    @Override
    protected String getFromSeparator() {
        return "/";
    }

    @Override
    protected String getToSeparator() {
        return File.separator;
    }

    public String getIconFilename() {
        return QueueFile.FILE_DOWNLOAD_ICON_FILENAME;
    }

    public void transmit(FTPModel ftpModel, FileQueue queue) throws IOException, FTPException {
        if(!ftpModel.getLocalDirectory().equals(this.destinationDirectory)){
            ftpModel.setLocalDirectory(this.destinationDirectory);
        }
        if(!ftpModel.getWorkDirectory().equals(this.sourceDirectory)){
            ftpModel.setWorkDirectory(this.sourceDirectory);
        }
        ftpModel.downloadFile(this);
    }

}
