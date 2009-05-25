package snailftp.view;

import snailftp.queuefile.QueueFile;
import snailftp.*;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jiangjizhong
 */
public class QueueFileTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component retValue = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof QueueFile) {
            QueueFile file = (QueueFile) value;
            this.setText(file.getFrom());
            this.setIcon(Util.getIcon(file.getIconFilename()));
        }
        return retValue;
    }
}
