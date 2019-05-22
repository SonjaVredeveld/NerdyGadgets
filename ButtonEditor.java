package kbs2;

import java.awt.*;
import javax.swing.*;

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private DriverScreen dc;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    public ButtonEditor(JCheckBox checkBox, JButton colButton) {
        super(checkBox);
        button = colButton;
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    // If button is selected following animation starts
    // Source: http://alvinalexander.com/java/swing/tame/table/ButtonEditor.java.shtml
    public Component getTableCellEditorComponent(JTable jtRouteTable, Object value,
            boolean isSelected, int row, int column) {
        // Button Animation
        if (isSelected) {
            button.setForeground(jtRouteTable.getSelectionForeground());
            button.setBackground(jtRouteTable.getSelectionBackground());
        } else {
            button.setForeground(jtRouteTable.getForeground());
            button.setBackground(jtRouteTable.getBackground());
        }

        if (value == null) {
            label = "";
        } else {
            label = value.toString();
        }
        button.setText(label);
        isPushed = true;
        return button;
    }

    public boolean isPushed() {
        return isPushed;
    }

    public void setPushed(boolean pushed) {
        isPushed = pushed;
    }

    public Object getCellEditorValue() {

        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
