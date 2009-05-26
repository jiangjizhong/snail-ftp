package snailftp.view;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;

import java.io.File;
import java.net.URL;
/**
 *
 * @author jiangjizhong
 */
class FileListCellRenderer extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component retValue = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof UIFile){
            UIFile uiFile = (UIFile)value;
            File f = uiFile.getFile();
            setIcon(uiFile.getIcon());
        }
        return retValue;
    }
}
