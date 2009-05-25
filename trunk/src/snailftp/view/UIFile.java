package snailftp.view;

import java.io.File;
import javax.swing.filechooser.FileSystemView;
import javax.swing.Icon;
/**
 *
 * @author jiangjizhong
 */
class UIFile implements Comparable<UIFile> {

    private static final FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    File file;

    private String displayName = null;
    private Icon icon;
    
       
    public UIFile(File f) {
        this.file = f;
    }

    public String getDisplayName(){
        return displayName;
    }
    
    @Override
    public String toString() {
        return getDisplayName() == null? fileSystemView.getSystemDisplayName(file) : getDisplayName();
    }

    public File getFile() {
        return file;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UIFile)) {
            return false;
        }
        UIFile other = (UIFile) obj;
        return this.file.equals(other.file);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.file != null ? this.file.hashCode() : 0);
        return hash;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Icon getIcon() {
        return icon == null ? fileSystemView.getSystemIcon(file) : icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int compareTo(UIFile other) {
        if(this.file.isDirectory() && other.file.isFile()) return -1;
        if(this.file.isFile() && other.file.isDirectory()) return 1;
        return this.file.compareTo(other.file);//按名称比较
    }
}
