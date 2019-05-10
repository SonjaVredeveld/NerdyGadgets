/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author Niek J Nijland
 */
public class RouteScreen extends JFrame implements ActionListener{
    private JTable JTRouteLocationList = new JTable();
    private JButton JBAssignRoute = new JButton();
    private JButton JBCancel = new JButton();
    private Route route;
    
    public RouteScreen() {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
