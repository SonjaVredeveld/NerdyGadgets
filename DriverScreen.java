/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

public class DriverScreen extends JFrame implements ActionListener, TableModelListener {

    private String SelectedRoute;
    private User user;
    private ArrayList<Route> routes;
    private Route route;
    private ArrayList<kbs2.Route> routeList = new ArrayList<>();
    private JTable jtRouteTable;
    private JLabel jtTitle;
    private JButton jbLogout;
    private JScrollPane tableSP;
    private ButtonEditor JBshowRoute;

    public DriverScreen(User user) {
        //Layout
        setLayout(new FlowLayout());
        setTitle("Beschikbare Routes");
        setPreferredSize(new Dimension(800, 600));

        jtTitle = new JLabel("Te rijden routes");
        jtTitle.setPreferredSize(new Dimension(800, 25));
        jtTitle.setHorizontalAlignment(JLabel.CENTER);

        this.user = user;
        
        //Test Column names
        String[] columnNames = {"Route nummer", "Aantal locaties", "Afstand", "Bekijk route", "Datum aangemaakt"};

        //Get rows for the table from database
        ArrayList<Route> routes = Route.getRoutes();
        this.routes = routes;
        Object[][] columnData = new Object[routes.size()][5];
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            Object[] row = {route.getID(), route.getLocations().size(), route.getDistance(), "Bekijk route", route.getCreationDate()};
            columnData[i] = row;
        }

        //Table Layout
        jtRouteTable = new JTable(columnData, columnNames);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jtRouteTable.getModel());
        jtRouteTable.setRowSorter(sorter);

        jtRouteTable.getModel().addTableModelListener(this);

        tableSP = new JScrollPane(jtRouteTable);
        tableSP.setPreferredSize(new Dimension(770, 450));
        tableSP.setAlignmentX(LEFT_ALIGNMENT);

        jtRouteTable.getColumn("Bekijk route").setCellRenderer(new ButtonRenderer());
        jtRouteTable.getColumn("Bekijk route").setCellEditor(new ButtonEditor(new JCheckBox()));
        // Turns columns ''Bekijk route" into buttons (more info found in ButtonRenderer and ButtonEditor)

        jbLogout = new JButton("Log uit");
        jbLogout.setBackground(new Color(158, 188, 237));
        jbLogout.setForeground(Color.BLACK);
        jbLogout.addActionListener(this);

        // Order for elements to appear
        add(jtTitle);
        add(tableSP);
        add(jbLogout);

        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Back to loginscreen
        if (e.getSource() == jbLogout) {
            dispose();
            LoginScreen LS = new LoginScreen();
            LS.setVisible(true);
        }
        // "Start route" buttons are located in the class ButtonEditor
    }

    //
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = jtRouteTable.getSelectedRow();
        int id = (int) jtRouteTable.getModel().getValueAt(row, 0);
        for (int i = 0; i < routes.size(); i++) {
            if(i == id){
                Route route = routes.get(i);
                this.route = route;
            }
        }
        DriverRouteScreen dialoog = new DriverRouteScreen(this, route);
        dialoog.setVisible(true);
        new DriverScreen(this.user);
        this.dispose();
    }
}
