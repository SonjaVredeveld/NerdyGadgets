/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author Niek J Nijland
 */
public class PlannerScreen extends JFrame implements ActionListener{
    private JTable JTOrderList = new JTable();
    private JButton JBStartRoute = new JButton();
    private JButton JBCancel = new JButton();
    private JButton JBLogout = new JButton();
    private ArrayList<Order> SelectedOrders = new ArrayList<>();
    
    public PlannerScreen(){
        
    }
    
    public void logout(){
        
    }
    
    public void addOrder(int ID){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
