/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriverRouteScreen extends JDialog implements ActionListener {

    private Route route;
    private JTable jtDeliveryRouteTable;
    private JButton jbSubmit;
    private JButton jbCancel;
    private JTable AvailableRouteLocations = new JTable();

    public DriverRouteScreen(JFrame screen, Route route) {
        super(screen, true);

        setTitle("Route overzicht");
        setSize(800, 600);
        setLayout(new FlowLayout());

        String[] columnNames = {"Ordernummer", "klant", "adres", "woonplaats"};
        this.route = route;
        Object[][] columnData = new Object[route.getLocations().size()][4];
        for (int i = 0; i < route.getLocations().size(); i++) {
            RouteLocation routlocation = route.getLocations().get(i);
            Order order = routlocation.getOrder();
            Customer customer = order.getCustomer();
            Object[] row = {order.getID(), customer.getCustomerName(), customer.getDeliveryAddressLine2(), customer.getCustomerCity()};
            columnData[i] = row;
        }

        jtDeliveryRouteTable = new JTable(columnData, columnNames);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jtDeliveryRouteTable.getModel());
        jtDeliveryRouteTable.setRowSorter(sorter);
        JScrollPane tableSP = new JScrollPane(jtDeliveryRouteTable);
        tableSP.setPreferredSize(new Dimension(800, 500));
        tableSP.setAlignmentX(LEFT_ALIGNMENT);

        //Buttons + ButtonLayour
        jbSubmit = new JButton("Route afgerond");
        jbSubmit.setBackground(new Color(158, 188, 237));
        jbSubmit.setForeground(Color.BLACK);
        jbSubmit.setBounds(100, 100, 100, 100);
        jbSubmit.addActionListener(this);

        jbCancel = new JButton("Annuleren");
        jbCancel.setBackground(new Color(158, 188, 237));
        jbCancel.setForeground(Color.BLACK);
        jbCancel.setBounds(100, 100, 100, 100);
        jbCancel.addActionListener(this);

        //Order for elements to appear
        add(tableSP);
        add(jbSubmit);
        add(jbCancel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbSubmit) {
            dispose();
            // MOET AANGEVEN DAT DE ROUTE IS GEREDEN
            int id = route.getID();
            for (int i = 0; i < route.getLocations().size(); i++) {
                RouteLocation routelocation = route.getLocations().get(i);
                Order order = routelocation.getOrder();
                order.ordersDelivered(order.getID());
                routelocation.deleteRows(id);
            }
            route.deleteRow(id);
        }
        if (e.getSource() == jbCancel) {
            dispose();

        }
    }
}
