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

public class DriverScreen extends JFrame implements ActionListener{
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

        //Test Column names
        String[] columnNames = { "Route nummer", "Aantal locaties", "Afstand", "Bekijk route", "Datum aangemaakt" };
        
        //Get rows for the table from database
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT r.RouteID, count(rl.RouteID), r.distanceKM, \"Bekijk route\", r.CreationDate FROM routes as r, routelocation as rl where DriverID is null and r.RouteID = rl.RouteID group by rl.RouteID order by r.creationDate;");
        String[][] columnData = new String[rows.size()][5];
        for(int i = 0; i < rows.size(); i++)
        {
            for(int j = 0; j < 5; j++)
            {
                columnData[i][j] = rows.get(i).get(j);
                
            }
        }

        //Table Layout
        jtRouteTable = new JTable(columnData, columnNames);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jtRouteTable.getModel());
        jtRouteTable.setRowSorter(sorter);

        tableSP = new JScrollPane(jtRouteTable);
        tableSP.setPreferredSize(new Dimension(800, 500));
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
    
    public ArrayList<Route> getRoutes() {
        return new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Back to loginscreen
        if(e.getSource() == jbLogout){
            dispose();
            LoginScreen LS = new LoginScreen();
            LS.setVisible(true);
        }
        // "Start route" buttons are located in the class ButtonEditor
    }
    
}