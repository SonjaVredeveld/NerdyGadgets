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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

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
        
        JTOrderList.setPreferredSize(new Dimension(780, 492));
        tableSP.setPreferredSize(new Dimension(780, 492));
        
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
                addOrder((int) JTOrderList.getModel().getValueAt(i,1));
            }
        }
    }

    
    //simple logout funtion
    public void logout(){
        ActiveUser = null;
        this.dispose();
    }
    
    //parm = OrderID of order to be added to SelectedOrders ArrayList
    public void addOrder(int ID){
        Order newOrder = new Order(ID);
        SelectedOrders.add(newOrder);
        
    }
    
    
    //calculates the optimal route with the given Orders, saves the route with its locations in the database
    public void routeTSP(ArrayList<Order> ar1){
        if(ar1.isEmpty()){
            JOptionPane.showMessageDialog(this,"Selecteer minimaal 1 order");
        }else if(ar1.size() <= 20){
            Route r1 = new Route(ar1); 
            if(r1.getResult()){
                //link to planner popup
            }else{
                JOptionPane.showMessageDialog(this,"Er ging iets fout bij het bereken van uw route");
            }
        }else{
            JOptionPane.showMessageDialog(this,"Meer dan 20 orders geselecteerd");      
        }
    }
}
