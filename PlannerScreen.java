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
        setTitle("Routeplanner");
        setPreferredSize(new Dimension(800, 600));

        JTOrderList = new JTable(new PlannerScreenTableContent());
        JTOrderList.getTableHeader().setReorderingAllowed(false);

        JTOrderList.getModel().addTableModelListener(this);
        //creating a ScrollPane from the JTable
        JScrollPane tableSP = new JScrollPane(JTOrderList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JTOrderList.setPreferredSize(new Dimension(800, 492));
        tableSP.setPreferredSize(new Dimension(800, 492));
        
        //setting the size for every column of JTOrderList
        TableColumn column = null;
        for (int i = 0; i < 4; i++) {
            if(i == 0){
                JTOrderList.getColumnModel().getColumn(i).setPreferredWidth(50);
            }else{
                JTOrderList.getColumnModel().getColumn(i).setPreferredWidth(100);        
            }
        } 
        add(tableSP);
        
        Panel p = new Panel();
        p.setLayout(new GridLayout(1,5));
        p.setPreferredSize(new Dimension(800, 50));
        JBStartRoute = style.button("Start routebepaling");
        JBStartRoute.addActionListener(this);
        JBLogout = style.button("Uitloggen");
        JBLogout.addActionListener(this);
        
        //elements in the lower part of the screen
        p.add(new JLabel(" "));
        p.add(new JLabel(" "));
        p.add(JBStartRoute);
        p.add(new JLabel(" "));
        p.add(JBLogout);
        
        add(p);
        
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
  
    //simple logout function
    private void logout(){
        ActiveUser = null;
        this.dispose();
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);
    }
    
    //calculates the optimal route with the given Orders, saves the route with its locations in the database
    private void routeTSP(ArrayList<Order> ar1){
        if(ar1.isEmpty()){
            JOptionPane.showMessageDialog(this,"Selecteer minimaal 1 order");
        }else if(ar1.size() <= 20){
            Route r1 = new Route(ar1); 
            if(r1.getResult()){
                new RouteScreen(this,r1);
            }else{
                JOptionPane.showMessageDialog(this,"Er ging iets fout bij het bereken van uw route");
            }
        }else{
            JOptionPane.showMessageDialog(this,"Meer dan 20 orders geselecteerd");      
        }
    }
}
