package kbs2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Niek J Nijland
 *
 */

public class RouteScreen extends JDialog implements ActionListener {
    private JTable JTRouteLocationList;
    private JButton JBAssignRoute;
    private JButton JBCancel;
    private ArrayList<Order> SelectedOrders = new ArrayList<>();
    private JLabel JLTitle;
    private Route route;
    
    
    //parm 1: JFrame to display this popup on
    //parm 2: Route to view data of in this popup
    public RouteScreen(JFrame screen, Route r1){
        super(screen, true);
        this.route = r1;
        setLayout(new FlowLayout());
        setTitle("Planner");
        setSize(500, 500);
        Panel panelTop = new Panel();
        panelTop.setLayout(new FlowLayout());
        panelTop.setPreferredSize(new Dimension(500, 50));
        //buttons in the top part of the screen
        ArrayList<ArrayList<String>> rows1 = DBConnection.selectQuery("SELECT distanceKM, RouteID FROM routes WHERE RouteID = (SELECT MAX(RouteID) FROM routelocation)");
        panelTop.add(new JLabel("Route: " + rows1.get(0).get(1), SwingConstants.CENTER));
        panelTop.add(new JLabel(" "));
        this.add(panelTop);

        
        
        Panel panelTop2 = new Panel();
        panelTop2.setLayout(new GridLayout(1,3));
        panelTop2.setPreferredSize(new Dimension(500, 50));
        //buttons in the top part of the screen
        panelTop2.add(new JLabel(" "));
        panelTop2.add(new JLabel("Totale afstand: " + rows1.get(0).get(0) + " KM", SwingConstants.CENTER));
        panelTop2.add(new JLabel(" "));
        this.add(panelTop2);

        String[] columnNames = {"Woonplaats","Adres","Klant"};
        JTRouteLocationList = new JTable(tableData(),columnNames);
        JTRouteLocationList.getTableHeader().setReorderingAllowed(false);

        //creating a ScrollPane from the JTable
        JScrollPane tableSP = new JScrollPane(JTRouteLocationList);
        
        JTRouteLocationList.setFillsViewportHeight(true);
        tableSP.setPreferredSize(new Dimension(320, 250));
        
        this.add(tableSP);
        
        
        Panel panelBottom = new Panel();
        panelBottom.setLayout(new GridLayout(1,5));
        panelBottom.setPreferredSize(new Dimension(650, 50));
        //buttons in the lower part of the screen
        panelBottom.add(new JLabel(" "));
        JBAssignRoute = style.button("Route toewijzen");
        JBAssignRoute.addActionListener(this);
        panelBottom.add(JBAssignRoute);
        panelBottom.add(new JLabel(" "));
        JBCancel = style.button("Annuleren");
        JBCancel.addActionListener(this);
        panelBottom.add(JBCancel);
        panelBottom.add(new JLabel(" "));
        this.add(panelBottom);
        
        //disables window resizing by the user
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    //return: double array with the data for the JTabel
    private Object[][] tableData() {
        Object[][] data;
        ArrayList<Order> OrderList = new ArrayList<>();
        ArrayList<RouteLocation> ar1 = this.route.getLocations();
        for (int i = 0; i < ar1.size(); i++) {
            OrderList.add(ar1.get(i).getOrder());
        }
        data = new Object[OrderList.size()][3];

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
        if(e.getSource() == JBAssignRoute) {
            //if Assign button is pressed, open popup. if the popup was closed and a driver has been assigned close this window
            AssignRouteScreen as1 = new AssignRouteScreen(new JFrame(), route);
            if(as1.getStatus()) {
                this.dispose();
            }
        }else if(e.getSource() == JBCancel) {
            //if cancel button was pressed, attempt to delete the newly created route and its routelocations, then prompt the corresponding popup
            boolean result1 = this.route.deleteRoute();
            if(result1) {
                JOptionPane.showMessageDialog(this, "Route geannuleerd", "Melding", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Er ging iets mis bij het annuleren", "foutmelding", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        }
    }
}