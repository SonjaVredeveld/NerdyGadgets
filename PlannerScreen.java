/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

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
        this.setLayout(new FlowLayout());
        this.setTitle("Planner");
        this.setPreferredSize(new Dimension(800, 600));

        JTOrderList = new JTable(new PlannerScreenTableContent());
        JTOrderList.getTableHeader().setReorderingAllowed(false);

        JTOrderList.getModel().addTableModelListener(this);
        //creating a ScrollPane from the JTable
        JScrollPane tableSP = new JScrollPane(JTOrderList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JTOrderList.setPreferredSize(new Dimension(800, 492));
        tableSP.setPreferredSize(new Dimension(800, 492));

        //setting the size for every column of JTOrderList

        setJTableColumnsWidth(JTOrderList, 800, 2, 14, 28, 28, 28);

        add(tableSP);
        
        Panel panelBottom = new Panel();
        panelBottom.setLayout(new GridLayout(1,5));
        panelBottom.setPreferredSize(new Dimension(800, 50));
        JBStartRoute = style.button("Start routebepaling");
        JBStartRoute.addActionListener(this);
        JBLogout = style.button("Uitloggen");
        JBLogout.addActionListener(this);
        
        //elements in the lower part of the screen
        panelBottom.add(new JLabel(" "));
        panelBottom.add(new JLabel(" "));
        panelBottom.add(JBStartRoute);
        panelBottom.add(new JLabel(" "));
        panelBottom.add(JBLogout);
        
        this.add(panelBottom);
        
        //disables window resizing by the user
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
                                             double... percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
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
  
    //simple logout function
    private void logout(){
        ActiveUser = null;
        this.dispose();
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);
    }
    
    //calculates the optimal route with the given Orders, saves the route with its locations in the database
    //parm 1: ArrayList with orders to be included in the route
    private void routeTSP(ArrayList<Order> ar1){
        if(ar1.isEmpty()){
            JOptionPane.showMessageDialog(this, "Selecteer minimaal 1 order", "foutmelding", JOptionPane.INFORMATION_MESSAGE);
        }else if(ar1.size() <= 20){
            Route r1 = new Route(ar1); 
            if(r1.getResult()){
                new RouteScreen(this,r1);           
            }else{
                JOptionPane.showMessageDialog(this, "Er ging iets fout bij het bereken van uw route", "foutmelding", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Er zijn meer dan 20 orders geselecteerd", "foutmelding", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
