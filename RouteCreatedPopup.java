package kbs2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class RouteCreatedPopup extends JDialog implements ActionListener {
    private JTable JTOrderList;
    private User ActiveUser;
    private JButton JBCancel;
    private JButton JBAssign;
    private ArrayList<Order> SelectedOrders = new ArrayList<>();
    private JLabel JLTitle;
    
    public RouteCreatedPopup(JFrame screen){
        super(screen, true);
        this.ActiveUser = ActiveUser;
        setLayout(new FlowLayout());
        setTitle("Routeplanner");
        setSize(500, 500);
        
        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(1,3));
        p1.setPreferredSize(new Dimension(800, 50));
        //buttons in the top part of the screen
        p1.add(new JLabel(" "));
        p1.add(new JLabel("Route", SwingConstants.CENTER));
        p1.add(new JLabel(" "));
        add(p1);

        String[] columnNames = {"Woonplaats","Adres","Klant"};
        JTOrderList = new JTable(popupData(),columnNames);
        JTOrderList.getTableHeader().setReorderingAllowed(false);

        //creating a ScrollPane from the JTable
        JScrollPane tableSP = new JScrollPane(JTOrderList);
        
        JTOrderList.setPreferredSize(new Dimension(320, 300));
        tableSP.setPreferredSize(new Dimension(320, 300));
        
        add(tableSP);
        
        
        Panel p2 = new Panel();
        p2.setLayout(new GridLayout(1,5));
        p2.setPreferredSize(new Dimension(800, 50));
        //buttons in the lower part of the screen
        p2.add(new JLabel(" "));
        JBAssign = style.button("Route toewijzen");
        JBAssign.addActionListener(this);
        p2.add(JBAssign);
        p2.add(new JLabel(" "));
        JBCancel = style.button("Cancel");
        JBCancel.addActionListener(this);
        p2.add(JBCancel);
        p2.add(new JLabel(" "));
        add(p2);
        
        //disables window resizing by the user
        setResizable(false);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private Object[][] popupData() {
        Object[][] data;
        ArrayList<Order> OrderList = new ArrayList<>();
        ArrayList<ArrayList<String>> rows1 = DBConnection.selectQuery("SELECT OrderID, RouteID FROM routelocation WHERE RouteID = (SELECT MAX(RouteID) FROM routelocation) ORDER BY RouteNumber ASC");
        for (int i = 0; i < rows1.size(); i++) {
            OrderList.add(new Order(Integer.parseInt(rows1.get(i).get(0))));
        }
        data = new Object[OrderList.size()][5];

        //get all orders from the database and add them to the data portion of the JTable
        for(int i = 0;i < OrderList.size();i++) {
            Order order = OrderList.get(i);
            Customer customer = order.getCustomer();
            data[i][0] = customer.getCustomerCity();
            data[i][1] = customer.getDeliveryAddressLine2();
            data[i][2] = customer.getCustomerName();
        }
        
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == JBAssign) {
            //link to assign route screen
        }else if(e.getSource() == JBCancel) {
            boolean result1 = deleteRoute();
            if(result1) {
                JOptionPane.showMessageDialog(this,"Route geannuleerd");
            }else{
                JOptionPane.showMessageDialog(this,"Er ging iets mis bij het annuleren");
            }
            this.dispose();
        }
    }
    
    //deleting the last inserted route and its locations
    //return: if it succeeded at doing so or not
    public boolean deleteRoute() {
        ArrayList<String> prepares = new ArrayList<>();
        ArrayList<ArrayList<String>> rows1 = DBConnection.selectQuery("SELECT MAX(RouteID) FROM routes");
        if(!rows1.isEmpty()) {
            ArrayList<String> prepares1 = new ArrayList<>();
            prepares1.add(rows1.get(0).get(0));
            int result1 = DBConnection.executeQuery("DELETE FROM routelocation WHERE RouteID = ?", prepares1);
            int result2 = DBConnection.executeQuery("DELETE FROM routes WHERE RouteID = ?", prepares1);
            if(result1 != 0 && result2 != 0) {
                return true;
            }else{
                                System.out.println("test1");
                return false;

            }
        }else{
            System.out.println("test2");
            return false;
        }
    }
}