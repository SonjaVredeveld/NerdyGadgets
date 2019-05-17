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
import java.util.ArrayList;

public class DriverRouteScreen extends JDialog implements ActionListener {
    private JTable jtDeliveryRouteTable;
    private JButton jbSubmit;
    private JButton jbCancel;
    private JTable AvailableRouteLocations = new JTable();
    private Route route;


    public DriverRouteScreen(JFrame screen){
        super(screen, true);

        setTitle("Route overzicht");
        setSize(800, 600);
        setLayout(new FlowLayout());

        //Test Column names
        String[] columnNames = { "Ordernummer", "klant", "adres", "woonplaats" };
        
        //Get rows for the table from database
        ArrayList<String> prepares = new ArrayList<>();
        String id = new DriverScreen(null).getSelectedRoute();
        prepares.add(id);
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("select o.OrderID, c.CustomerName, c.DeliveryAddressLine2, c.PostalAddressLine2 from orders as o, customers as c, routelocation as rl where c.CustomerID = o.CustomerID and rl.OrderID = o.orderID and rl.RouteID = ?", prepares);
        String[][] columnData = new String[rows.size()][4];
        for(int i = 0; i < rows.size(); i++)
        {
            for(int j = 0; j < 4; j++)
            {
                columnData[i][j] = rows.get(i).get(j);
                
            }
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
        jbSubmit.setBounds(100,100,100,100);
        jbSubmit.addActionListener(this);


        jbCancel = new JButton("Annuleren");
        jbCancel.setBackground(new Color(158, 188, 237));
        jbCancel.setForeground(Color.BLACK);
        jbCancel.setBounds(100,100,100,100);
        jbCancel.addActionListener(this);

        //Order for elements to appear
        add(tableSP);
        add(jbSubmit);
        add(jbCancel);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbSubmit){
            dispose();
            // MOET AANGEVEN DAT DE ROUTE IS GEREDEN
        } if(e.getSource() == jbCancel){
            dispose();

        }
    }
}
