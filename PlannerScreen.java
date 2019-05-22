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
import javax.swing.table.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Niek J Nijland
 */
public class PlannerScreen extends JFrame implements ActionListener, TableModelListener{
    private JTable JTOrderList;
    private User ActiveUser;
    private JButton JBStartRoute = new JButton();
    private JButton JBCancel = new JButton();
    private JButton JBLogout;
    private ArrayList<Order> SelectedOrders = new ArrayList<>();
    private JLabel JLTitle;
    private JCheckBox cellCheck;
    
    public PlannerScreen(User ActiveUser){
        this.ActiveUser = ActiveUser;
        setLayout(new FlowLayout());
        setTitle("Planner");
        setPreferredSize(new Dimension(800, 600));
        
        //notification at the top of the window
        Panel panelTop = new Panel();
        panelTop.setLayout(new FlowLayout());
        panelTop.setPreferredSize(new Dimension(800, 50));
        panelTop.add(new JLabel("<html>Bij routeberekeningen met meer dan 17 orders word de alternatieve berekenmethode gebruikt,<br/>deze werkt sneller maar geeft minder accurate routes.</html>", SwingConstants.CENTER));
        add(panelTop);

        //creating a JTable in a JScrollPane
        JTOrderList = new JTable(new PlannerScreenTableContent());
        JTOrderList.getTableHeader().setReorderingAllowed(false);
        JTOrderList.getModel().addTableModelListener(this);
        JScrollPane tableSP = new JScrollPane(JTOrderList);
        JTOrderList.setFillsViewportHeight(true);
        tableSP.setPreferredSize(new Dimension(780, 440));
        
        //setting the size for every column of JTOrderList
        for (int i = 0; i < 4; i++) {
            if(i == 0){
                JTOrderList.getColumnModel().getColumn(i).setPreferredWidth(50);
            }else{
                JTOrderList.getColumnModel().getColumn(i).setPreferredWidth(100);        
            }
        } 
        add(tableSP);
        
        //buttons at the bottom of the window
        Panel PanelBottom = new Panel();
        PanelBottom.setLayout(new GridLayout(1,5));
        PanelBottom.setPreferredSize(new Dimension(800, 50));
        JBStartRoute = style.button("Start routebepaling");
        JBStartRoute.addActionListener(this);
        JBLogout = style.button("Uitloggen");
        JBLogout.addActionListener(this);
        PanelBottom.add(new JLabel(" "));
        PanelBottom.add(new JLabel(" "));
        PanelBottom.add(JBStartRoute);
        PanelBottom.add(new JLabel(" "));
        PanelBottom.add(JBLogout);
        add(PanelBottom);
        
        //disables window resizing by the user
        setResizable(false);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == JBLogout) {
            logout();
        }else if(e.getSource() == JBStartRoute){
                routeTSP(SelectedOrders);
                new PlannerScreen(this.ActiveUser);
                this.dispose();
        }else {
             JTOrderList.setCellSelectionEnabled(cellCheck.isSelected());
        }
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        SelectedOrders.clear();
        //if there are any checkmarks checked, call the addOrder-function to them.
        for(int i=0;i<JTOrderList.getModel().getRowCount();i++) {
            if ((boolean) JTOrderList.getModel().getValueAt(i,0)) {  
                SelectedOrders.add(new Order((int) JTOrderList.getModel().getValueAt(i,1)));
            }
        }
    }
  
    //simple logout funtion
    private void logout(){
        ActiveUser = null;
        this.dispose();
    }
    
    //calculates the optimal route with the given Orders, saves the route with its locations in the database
    private void routeTSP(ArrayList<Order> ar1){
        if(ar1.isEmpty()){
            JOptionPane.showMessageDialog(this, "Selecteer minimaal 1 order", "foutmelding", JOptionPane.INFORMATION_MESSAGE);
        }else{
            Route r1 = new Route(ar1); 
            if(r1.getResult()){
                new RouteScreen(this,r1);
            }else{
                JOptionPane.showMessageDialog(this, "Er ging iets fout bij het bereken van uw route", "foutmelding", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
