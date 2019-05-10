package com.company;

import javax.swing.*;
import java.awt.*;

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

    public Component getTableCellEditorComponent(JTable jtRouteTable, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(jtRouteTable.getSelectionForeground());
            button.setBackground(jtRouteTable.getSelectionBackground());
        } else {
            button.setForeground(jtRouteTable.getForeground());
            button.setBackground(jtRouteTable.getBackground());
        }
        label = (value == null) ? "" : value.toString();
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
        if (isPushed) {
            DriverRouteScreen dialoog = new DriverRouteScreen(dc);
            dialoog.setVisible(true);
        }
        isPushed = false;
        return new String(label);
    } // Afhandeling van de start route knoppen

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
