package kbs2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonEditor extends DefaultCellEditor implements ActionListener {
    protected JButton button;
    private String label;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);
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

        if(value == null){
            label = "";
        } else {
            label = value.toString();
        }

        button.setText(label);
        return button;
    }

    public Object getCellEditorValue() {
        return new String(label);
    }

    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.fireEditingStopped();
    }
}
