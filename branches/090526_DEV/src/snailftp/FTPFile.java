
package snailftp;

import java.util.Date;
import java.lang.Comparable;
/**
 * FTP文件模型
 * @author jiangjizhong
 */
public class FTPFile implements Comparable<FTPFile> {
    private String name;
    private long size;
    private Date createAt;
    private String owner;
    private String group;
    private String attribute;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    /**
     * 判断文件是否为目录类型
     * @return
     */
    public boolean isDirectory(){
        if(this.attribute.startsWith("d"))
            return true;
        else
            return false;
    }
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(!(obj instanceof FTPFile)) return false;
        FTPFile other = (FTPFile)obj;
        if(!(Util.equals(this.name, other.name))) return false;
        if(this.isDirectory() != other.isDirectory()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.attribute != null ? this.attribute.hashCode() : 0);
        return hash;
    }

    public int compareTo(FTPFile other) {
        //文件夹比文件小
        if(this.isDirectory() && !other.isDirectory()) return -1;
        if(!this.isDirectory() && other.isDirectory()) return 1;
        return this.getName().compareTo(other.getName());
    }
}
