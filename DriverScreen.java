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
    private JLabel jtTitle, JLinfo;
    private JButton jbLogout;
    private JScrollPane tableSP;
    private User ActiveUser;

    public DriverScreen(User user) {
        this.user = user;
        if(!user.getLevel().equals("Driver")){
            this.dispose();
            new LoginScreen();
        }
        //Layout
        setLayout(new FlowLayout());
        setTitle("Beschikbare Routes");
        setPreferredSize(new Dimension(800, 600));

        jtTitle = new JLabel("Te rijden routes");
        jtTitle.setPreferredSize(new Dimension(800, 25));
        jtTitle.setHorizontalAlignment(JLabel.CENTER);
        
        JLinfo = new JLabel("De moet van boven naar beneden worden afgewerkt.");
        JLinfo.setPreferredSize(new Dimension(800, 25));
        JLinfo.setHorizontalAlignment(JLabel.CENTER);

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

        jbLogout = style.button("Log uit");
        jbLogout.addActionListener(this);

        Panel p = new Panel();
        p.setLayout(new GridLayout(1, 5));
        p.setPreferredSize(new Dimension(800, 50));

        // Order for elements to appear
        add(jtTitle);
        add(JLinfo);
        add(tableSP);
        p.add(new JLabel(" "));
        p.add(new JLabel(" "));
        p.add(new JLabel(" "));
        p.add(new JLabel(" "));
        p.add(jbLogout);
        add(p);

        setResizable(false);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void logout() {
        ActiveUser = null;
        this.dispose();
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);
    }
    @Override
    public void actionPerformed (ActionEvent e){
        //Back to loginscreen
        if (e.getSource() == jbLogout) {
            logout();
        }
        // "Start route" buttons are located in the class ButtonEditor
    }

    @Override
    public void tableChanged (TableModelEvent e){
        int row = jtRouteTable.getSelectedRow();
        int id = (int) jtRouteTable.getModel().getValueAt(row, 0);
        for (int i = 0; i < routes.size(); i++) {
            if (i == id) {
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
