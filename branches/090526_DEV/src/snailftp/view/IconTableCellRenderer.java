
package snailftp.view;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author jiangjizhong
 */
public class IconTableCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component retValue = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value instanceof Icon){
            this.setText("");
            this.setIcon((Icon)value);
        }
        return retValue;
    }
}
