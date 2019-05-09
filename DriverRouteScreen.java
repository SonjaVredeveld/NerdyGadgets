/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;

/**
 *
 * @author Niek J Nijland
 */
public class DriverRouteScreen extends JDialog implements ActionListener{
    private JTable AvailableRouteLocations = new JTable();
    private Route route = new Route();
    private JButton JBCancel = new JButton();
    private JButton JBSubmit = new JButton();
    
    public DriverRouteScreen() {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
        
}
