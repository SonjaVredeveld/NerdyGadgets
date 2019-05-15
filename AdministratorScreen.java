/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author Niek J Nijland
 */
public class AdministratorScreen extends JFrame implements ActionListener{
    private JTabbedPane JTPAdminTabs = new JTabbedPane();
    private JTable JTStock = new JTable();
    private JTable JTCustomers = new JTable();
    private JTable JTOrders = new JTable();
    
    public AdministratorScreen() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    
}
