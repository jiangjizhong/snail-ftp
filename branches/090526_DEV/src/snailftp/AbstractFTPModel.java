package snailftp;

import java.util.*;
/**
 *
 * @author jiangjizhong
 */
public abstract class AbstractFTPModel implements FTPModel {

    protected List<ReplyObserver> replyObserver = new ArrayList<ReplyObserver>();
    protected List<CommandObserver> commandObserver = new ArrayList<CommandObserver>();
    protected List<CurrentDirectoryObserver> currentDirectoryObserver = new ArrayList<CurrentDirectoryObserver>();
    protected List<FTPFileListObserver> ftpFileListObserver = new ArrayList<FTPFileListObserver>();
    protected List<FileTransmitObserver> fileTransmitObserver = new ArrayList<FileTransmitObserver>();
    protected List<ModelStateObserver> modelStateObserver = new ArrayList<ModelStateObserver>();
    protected List<MessageObserver> messageObserver = new ArrayList<MessageObserver>();
    protected OverridePolicyMaker overridePolicyMaker = null;
    
    private int ioBufferSize = FTPModel.DEFAULT_IO_BUFFER_SIZE;
    private FTPFileFilter ftpFileFilter = new DefaultFTPFileFilter();
    
    private String currentFilename;
    private long transferedSize;
    private long totalSize;
    private long timePassed;
    
    public void addReplyObserver(ReplyObserver observer) {
        this.replyObserver.add(observer);
    }

    public void removeReplyObserver(ReplyObserver observer) {
        this.replyObserver.remove(observer);
    }

    public void addCommandObserver(CommandObserver observer) {
        this.commandObserver.add(observer);
    }

    public void removeCommandObserver(CommandObserver observer) {
        this.commandObserver.remove(observer);
    }

    public void addFileListObserver(FTPFileListObserver observer) {
        this.ftpFileListObserver.add(observer);
    }

    public void removeFileListObserver(FTPFileListObserver observer) {
        this.ftpFileListObserver.remove(observer);
    }

    public void addCurrentDirectoryObserver(CurrentDirectoryObserver observer) {
        this.currentDirectoryObserver.add(observer);
    }

    public void removeCurrentDirectoryObserver(CurrentDirectoryObserver observer) {
        this.currentDirectoryObserver.remove(observer);
    }

    public void setOverridePolicyMaker(OverridePolicyMaker maker) {
        this.overridePolicyMaker = maker;
    }

    public void addFileTransmitObserver(FileTransmitObserver observer) {
        this.fileTransmitObserver.add(observer);
    }

    public void remvoeFileTransmitObserver(FileTransmitObserver observer) {
        this.fileTransmitObserver.remove(observer);
    }
    
    public void addModelStateObserver(ModelStateObserver observer) {
        this.modelStateObserver.add(observer);
    }

    public void removeModelStateObserver(ModelStateObserver observer) {
        this.modelStateObserver.remove(observer);
    }

    public void addMessageObserver(MessageObserver observer) {
        this.messageObserver.add(observer);
    }

    public void removeMessageObserver(MessageObserver observer) {
        this.messageObserver.remove(observer);
    }
    
    public void setIOBufferSize(int ioBufferSize){
        if(ioBufferSize < 1){
            throw new java.lang.IllegalArgumentException("IO缓冲区最小为1");
        }
        this.ioBufferSize = ioBufferSize;
    }
    
    public int getIOBufferSize(){
        return this.ioBufferSize;
    }

    public FTPFileFilter getFtpFileFilter() {
        return ftpFileFilter;
    }

    public void setFtpFileFilter(FTPFileFilter ftpFileFilter) {
        this.ftpFileFilter = ftpFileFilter;
    }

    public String getCurrentFilename() {
        return currentFilename;
    }

    protected void setCurrentFilename(String currentFilename) {
        this.currentFilename = currentFilename;
    }

    public long getTransferedSize() {
        return transferedSize;
    }

    protected void setTransferedSize(long transferedSize) {
        this.transferedSize = transferedSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    protected void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTimePassed() {
        return timePassed;
    }

    protected void setTimePassed(long timePassed) {
        this.timePassed = timePassed;
    }
}
