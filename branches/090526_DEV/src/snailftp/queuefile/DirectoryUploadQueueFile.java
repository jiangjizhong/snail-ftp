package snailftp.queuefile;

import snailftp.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author jiangjizhong
 */
public class DirectoryUploadQueueFile extends AbstractQueueFile {

    public DirectoryUploadQueueFile(String name, String srcDir, String destDir, long size) {
        super(name, srcDir, destDir, size);
    }

    public String getIconFilename() {
        return QueueFile.DIRECTORY_UPLOAD_ICON_FILENAME;
    }

    public void transmit(FTPModel ftpModel, FileQueue queue) throws IOException, FTPException {
        if (!ftpModel.getLocalDirectory().equals(this.sourceDirectory)) {
            ftpModel.setLocalDirectory(this.sourceDirectory);
        }
        if (!ftpModel.getWorkDirectory().equals(this.destinationDirectory)) {
            ftpModel.setWorkDirectory(this.destinationDirectory);
        }
        List<FTPFile> list = ftpModel.getFileList();
        boolean existDirectory = false;
        for (FTPFile file : list) {
            if (file.isDirectory() && file.getName().equals(this.name)) {
                existDirectory = true;
                break;
            }
        }
        if (!existDirectory) {
            ftpModel.makeDir(name);
            if(!ftpModel.getReply().isPositiveCompletion()){
                //建立文件夹失败
                return;
            }
        }
        //载入本地文件到队列中
        ftpModel.setLocalDirectory(this.getFrom());
        ftpModel.setWorkDirectory(name);
        File localDirectory = new File(this.getFrom());
        assert localDirectory.isDirectory();
        File[] files = localDirectory.listFiles(new FileFilter() {
            public boolean accept(File f) {
                return !f.isHidden();
            }
        });
        for(File f : files){
            QueueFile queueFile = null;
            if(f.isDirectory()){
                queueFile = new DirectoryUploadQueueFile(f.getName(),
                    ftpModel.getLocalDirectory(), ftpModel.getWorkDirectory(), f.length());
            }else{
                queueFile = new FileUploadQueueFile(f.getName(),
                    ftpModel.getLocalDirectory(), ftpModel.getWorkDirectory(), f.length());
            }
            queue.addFirst(queueFile);
        }
    }

    @Override
    protected String getFromSeparator() {
        return File.separator;
    }

    @Override
    protected String getToSeparator() {
        return "/";
    }
}
