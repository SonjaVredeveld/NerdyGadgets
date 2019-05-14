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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author Niek J Nijland
 */
public class PlannerScreen extends JFrame implements ActionListener{
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

        //checking if table was changed
        JTOrderList.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                //if there are any checkmarks checked, call the addOrder-function to them.
                for(int i=0;i<JTOrderList.getModel().getRowCount();i++) {
                    if ((boolean) JTOrderList.getModel().getValueAt(i,0)) {  
                        addOrder((int) JTOrderList.getModel().getValueAt(i,1));
                    }
                }
            }
        });
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
        JBLogout = style.button("Uitloggen");
        JBLogout.addActionListener(this);
        
        //buttons in the lower part of the screen
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
        }else {
             JTOrderList.setCellSelectionEnabled(cellCheck.isSelected());
        }
    }
    
    //simple logout funtion
    public void logout(){
        ActiveUser = null;
    }
    
    //parm = OrderID of order to be added to SelectedOrders ArrayList
    public void addOrder(int ID){
        //check of orderID already exists in ArrayList SelectedOrders, if not add it.
        ArrayList<Integer> SelectedOrderIDs = new ArrayList<>();
        SelectedOrders.forEach((SelectedOrder) -> {
            SelectedOrderIDs.add(SelectedOrder.getID());
        });

        if (!SelectedOrderIDs.contains(ID)) {
            SelectedOrders.add(new Order(ID));
        }
    }
}
