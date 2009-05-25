
package snailftp.view;

import snailftp.*;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author jiangjizhong
 */
public class FileSizeTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component retValue = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value instanceof Long){
            this.setText(Util.formatFileSize((Long)value));
        }
        return retValue;
    }
}
