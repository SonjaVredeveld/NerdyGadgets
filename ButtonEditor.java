/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import javax.swing.*;
import java.awt.*;

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private AdministratorScreen admin;
    private String type;

    public ButtonEditor(JCheckBox checkBox, String type) {
        super(checkBox);
        this.type = type;
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
    if(type == "products") {
        if (isPushed) {
            EditStock dialoog = new EditStock(admin);
            dialoog.setVisible(true);
        }
            isPushed = false;
    } else if(type == "customers"){
        if (isPushed) {
            EditCustomer dialoog = new EditCustomer(admin);
            dialoog.setVisible(true);
        }
            isPushed = false;
    } 
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