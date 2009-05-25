
package snailftp.queuefile;

import snailftp.*;
import snailftp.queuefile.FileDownloadQueueFile;
import java.io.*;
import java.util.*;
/**
 *
 * @author jiangjizhong
 */
public class DirectoryDownloadQueueFile extends AbstractQueueFile {

    public DirectoryDownloadQueueFile(String name, String srcDir, String destDir, long size){
        super(name, srcDir, destDir, size);
    }

    public String getIconFilename() {
        return QueueFile.DIRECTORY_DOWNLOAD_ICON_FILENAME;
    }

    public void transmit(FTPModel ftpModel, FileQueue queue) throws IOException, FTPException {
        if(!ftpModel.getLocalDirectory().equals(this.destinationDirectory)){
            ftpModel.setLocalDirectory(this.destinationDirectory);
        }
        if(!ftpModel.getWorkDirectory().equals(this.sourceDirectory)){
            ftpModel.setWorkDirectory(this.sourceDirectory);
        }
        //建文件夹
        File localDirectory = new File(this.getTo());
        if(!localDirectory.exists()){
            if(!localDirectory.mkdirs()){
                return;//建文件夹失败，返回
            }
        }
        //载入文件
        ftpModel.setLocalDirectory(this.getTo());
        ftpModel.setWorkDirectory(name);
        List<FTPFile> list = ftpModel.getFileList();
        for(FTPFile file : list){
            QueueFile queueFile = null;
            if(file.isDirectory()){
                queueFile = new DirectoryDownloadQueueFile(file.getName(),
                        ftpModel.getWorkDirectory(), ftpModel.getLocalDirectory(), file.getSize());
            }else{
                queueFile = new FileDownloadQueueFile(file.getName(),
                        ftpModel.getWorkDirectory(), ftpModel.getLocalDirectory(), file.getSize());
            }
            queue.addFirst(queueFile);
        }
    }

    @Override
    protected String getFromSeparator() {
        return "/";
    }

    @Override
    protected String getToSeparator() {
        return File.separator;
    }
}
