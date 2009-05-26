
package snailftp.queuefile;

import snailftp.*;
import java.io.File;
/**
 * 队列文件模型
 * @author jiangjizhong
 */
public abstract class AbstractQueueFile implements QueueFile {
    protected String sourceDirectory;
    protected String name;
    protected String destinationDirectory;
    protected long size;

    public AbstractQueueFile(String name, String srcDir, String destDir, long size){
        this.name = name;
        this.sourceDirectory = srcDir;
        this.destinationDirectory = destDir;
        this.size = size;
    }
    
    public String getSourceDirectory(){
        return this.sourceDirectory;
    }
    
    public String getDestinationDirectory(){
        return this.destinationDirectory;
    }
    
    public String getName(){
        return this.name;
    }
    
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    
    public String getFrom(){
        String separator = this.getFromSeparator();
        return (this.sourceDirectory.endsWith(separator) 
                ? this.sourceDirectory : this.sourceDirectory + separator) + this.name;
    }
    
    protected abstract String getFromSeparator();
    protected abstract String getToSeparator();
    
    public String getTo(){
        String separator = this.getToSeparator();
        return (this.destinationDirectory.endsWith(separator) 
                ? this.destinationDirectory  : this.destinationDirectory + separator) + this.name;
    }
}
