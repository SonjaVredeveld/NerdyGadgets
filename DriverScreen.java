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
public class DriverScreen extends JFrame implements ActionListener{
    private JTable JTAvailableroutes = new JTable();
    private JButton JBShowroute = new JButton();
    private ArrayList<Route> routeList = new ArrayList<>();
    
    public DriverScreen() {
        
    }
    
    public Route getRoute() {
        return new Route();
    }
    
    public ArrayList<Route> getRoutes() {
        return new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
