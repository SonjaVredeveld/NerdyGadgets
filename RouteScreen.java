package kbs2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class RouteScreen extends JDialog implements ActionListener {
    private JTable JTRouteLocationList;
    private User ActiveUser;
    private JButton JBAssignRoute;
    private JButton JBCancel;
    private ArrayList<Order> SelectedOrders = new ArrayList<>();
    private JLabel JLTitle;
    
    public RouteScreen(JFrame screen, Route r1){
        super(screen, true);
        this.ActiveUser = ActiveUser;
        setLayout(new FlowLayout());
        setTitle("Planner");
        setSize(500, 500);
        
        Panel panelTop = new Panel();
        panelTop.setLayout(new GridLayout(1,3));
        panelTop.setPreferredSize(new Dimension(500, 50));
        //buttons in the top part of the screen
        panelTop.add(new JLabel(" "));
        ArrayList<ArrayList<String>> rows1 = DBConnection.selectQuery("SELECT distanceKM, RouteID FROM routes WHERE RouteID = (SELECT MAX(RouteID) FROM routelocation)");
        panelTop.add(new JLabel("Route: " + rows1.get(0).get(1), SwingConstants.CENTER));
        panelTop.add(new JLabel(" "));
        add(panelTop);
        
        
        Panel panelTop2 = new Panel();
        panelTop2.setLayout(new GridLayout(1,3));
        panelTop2.setPreferredSize(new Dimension(500, 50));
        //buttons in the top part of the screen
        panelTop2.add(new JLabel(" "));
        panelTop2.add(new JLabel("Totale afstand: " + rows1.get(0).get(0) + " KM", SwingConstants.CENTER));
        panelTop2.add(new JLabel(" "));
        add(panelTop2);

        String[] columnNames = {"Woonplaats","Adres","Klant"};
        JTRouteLocationList = new JTable(tableData(r1),columnNames);
        JTRouteLocationList.getTableHeader().setReorderingAllowed(false);

        //creating a ScrollPane from the JTable
        JScrollPane tableSP = new JScrollPane(JTRouteLocationList);
        
        JTRouteLocationList.setPreferredSize(new Dimension(320, 250));
        tableSP.setPreferredSize(new Dimension(320, 250));
        
        add(tableSP);
        
        
        Panel panelBottom = new Panel();
        panelBottom.setLayout(new GridLayout(1,5));
        panelBottom.setPreferredSize(new Dimension(650, 50));
        //buttons in the lower part of the screen
        panelBottom.add(new JLabel(" "));
        JBAssignRoute = style.button("Route toewijzen");
        JBAssignRoute.addActionListener(this);
        panelBottom.add(JBAssignRoute);
        panelBottom.add(new JLabel(" "));
        JBCancel = style.button("Cancel");
        JBCancel.addActionListener(this);
        panelBottom.add(JBCancel);
        panelBottom.add(new JLabel(" "));
        add(panelBottom);
        
        //disables window resizing by the user
        setResizable(false);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private Object[][] tableData(Route r1) {
        Object[][] data;
        ArrayList<Order> OrderList = new ArrayList<>();
        ArrayList<RouteLocation> ar1 = r1.getLocations();
        for (int i = 0; i < ar1.size(); i++) {
            OrderList.add(ar1.get(i).getOrder());
        }
        data = new Object[OrderList.size() - 1][3];

        //get all orders from the database and add them to the data portion of the JTable
        for(int i = 0;i < OrderList.size() - 1;i++) {
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
        if(e.getSource() == JBAssignRoute) {
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
    private boolean deleteRoute() {
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