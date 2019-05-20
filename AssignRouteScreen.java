/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author Niek J Nijland
 */

public class AssignRouteScreen extends JDialog implements ActionListener, ListSelectionListener {
    private JButton JBAssign;
    private JTable JTDrivers;
    private User user;
    private Route route;
    private ArrayList<Driver> driverList;
    private Driver selectedDriver;
    private boolean status = false;

    public AssignRouteScreen(JFrame screen, Route route){
        super(screen, true);
        setLayout(new FlowLayout());
        setTitle("Planner");
        setSize(500, 500);
        this.route = route;

        //panel with centered label top of screen
        Panel panelTop = new Panel();
        panelTop.setLayout(new FlowLayout());
        panelTop.setPreferredSize(new Dimension(500, 50));
        panelTop.add(new JLabel("Route: " + this.route.getID(), SwingConstants.CENTER));
        add(panelTop);

        String[] columnNames = {"Chauffeur"};
        JTDrivers = new JTable(tableData(),columnNames);
        JTDrivers.getTableHeader().setReorderingAllowed(false);
        JTDrivers.setCellSelectionEnabled(true);
        JTDrivers.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JTDrivers.getSelectionModel().addListSelectionListener(this);

        //creating a ScrollPane from the JTable
        JScrollPane tableSP = new JScrollPane(JTDrivers);

        JTDrivers.setPreferredSize(new Dimension(320, 250));
        tableSP.setPreferredSize(new Dimension(320, 250));
        add(tableSP);
        
        Panel panelBottom = new Panel();
        panelBottom.setLayout(new FlowLayout());
        panelBottom.setPreferredSize(new Dimension(500, 50));
        JBAssign = style.button("Route toewijzen");
        JBAssign.addActionListener(this);
        panelBottom.add(JBAssign);
        add(panelBottom);

        //disables window resizing by the user
        setResizable(false);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    //return: double array with the data for the JTabel 
    private Object[][] tableData() {
        driverList = Driver.getDrivers();
        Object[][] data = new Object[driverList.size() - 1][1];

        for(int i = 0;i < driverList.size() - 1;i++) {
            data[i][0] = driverList.get(i).getFullName();
        }
        
        return data;
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == JBAssign) {
            //add driver to route
            if(selectedDriver != null) {
                route.addDriver(selectedDriver);
                if(1 == 1) {
                    JOptionPane.showMessageDialog(this, "Route toegewezen aan: " + selectedDriver.getFullName(), "Melding", JOptionPane.INFORMATION_MESSAGE);
                    this.status = true;
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(this, "Er ging iets mis bij het toewijzen van de route", "Foutmelding", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Selecteer een chauffeur", "Foutmelding", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] selectedRow = JTDrivers.getSelectedRows();
        this.selectedDriver = this.driverList.get(selectedRow[0]);
    }
    
    //return: if driver has been added or not
    public boolean getStatus() {
        return this.status;
    }
        
}
