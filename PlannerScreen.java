/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author Niek J Nijland
 */
public class PlannerScreen extends JFrame implements ActionListener{
    private JTable JTOrderList;
    private JButton JBStartRoute = new JButton();
    private JButton JBCancel = new JButton();
    private JButton jbLogout = new JButton();
    private ArrayList<Order> SelectedOrders = new ArrayList<>();
    private JLabel JLTitle;
    private JButton jbUitloggen;
    private JButton jbStartRouteBepaling;
    private JTextArea output;
    private JCheckBox rowCheck;
    private JCheckBox columnCheck;
    private JCheckBox cellCheck;
    
    public PlannerScreen(){
        setLayout(new FlowLayout());
        setTitle("Routeplanner");
        setPreferredSize(new Dimension(800, 800));

        JTOrderList = new JTable(new PlannerScreenTableContent());
        JTOrderList.getTableHeader().setReorderingAllowed(false);
        //setting the size of the JTable
        JTOrderList.setPreferredSize(new Dimension(780, 492));
        JScrollPane tableSP = new JScrollPane(JTOrderList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //setting the size of the scrolling part, of which the JTable is content
        tableSP.setPreferredSize(new Dimension(780, 492));
        
        //setting the size for every column of JTOrderList
        TableColumn column;
        column = null;
        for (int i = 0; i < 4; i++) {
            column = JTOrderList.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(50);
                    break;
                case 1:
                    column.setPreferredWidth(100);
                    break;
                case 2:
                    column.setPreferredWidth(100);
                    break;
                case 3:
                    column.setPreferredWidth(100);
                    break;
                case 4:
                    column.setPreferredWidth(100);
                    break;
                default:
                    break;
            }
        } 
        add(tableSP);
        
        Panel p = new Panel();
        p.setLayout(new GridLayout(1,5));
        p.setPreferredSize(new Dimension(800, 50));
        jbStartRouteBepaling = style.button("Start routebepaling");
        jbUitloggen = style.button("Uitloggen");
        
        p.add(new JLabel(" "));
        p.add(new JLabel(" "));
        p.add(jbStartRouteBepaling);
        p.add(new JLabel(" "));
        p.add(jbUitloggen);
        
        add(p);
        
       
        //disables window resizing by the user
        setResizable(false);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
    }

    private JCheckBox addCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.addActionListener(this);
        add(checkBox);
        return checkBox;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        //Cell selection is disabled in Multiple Interval Selection
        //mode. The enabled state of cellCheck is a convenient flag
        //for this status.
            JTOrderList.setCellSelectionEnabled(cellCheck.isSelected());

        //Update checkboxes to reflect selection mode side effects.
        rowCheck.setSelected(JTOrderList.getRowSelectionAllowed());
        columnCheck.setSelected(JTOrderList.getColumnSelectionAllowed());
        if (cellCheck.isEnabled()) {
            cellCheck.setSelected(JTOrderList.getCellSelectionEnabled());
        }
    }

    private void outputSelection() {
        output.append(String.format("Lead: %d, %d. ",
                    JTOrderList.getSelectionModel().getLeadSelectionIndex(),
                    JTOrderList.getColumnModel().getSelectionModel().
                        getLeadSelectionIndex()));
        output.append("Rows:");
        for (int c : JTOrderList.getSelectedRows()) {
            output.append(String.format(" %d", c));
        }
        output.append(".\n");
    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("ROW SELECTION EVENT. ");
            outputSelection();
        }
    }
    
    public void logout(){
        
    }
    
    public void addOrder(int ID){
        
    }
}
