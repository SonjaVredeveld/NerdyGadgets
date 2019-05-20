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

public class DriverScreen extends JFrame implements ActionListener {

    private JTable jtRouteTable;
    private JLabel jtTitle;
    private JButton jbLogout;
    private JScrollPane tableSP;
    private User ActiveUser;

    public DriverScreen(User ActiveUser) {
        //Layout
        this.ActiveUser = ActiveUser;
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
        tableSP.setPreferredSize(new Dimension(800, 485));
        tableSP.setAlignmentX(LEFT_ALIGNMENT);

        jtRouteTable.getColumn("Bekijk route").setCellRenderer(new ButtonRenderer());
        jtRouteTable.getColumn("Bekijk route").setCellEditor(new ButtonEditor(new JCheckBox()));
        // Turns columns ''Bekijk route" into buttons (more info found in ButtonRenderer and ButtonEditor)

        jbLogout = style.button("Log uit");
        jbLogout.addActionListener(this);

        Panel p = new Panel();
        p.setLayout(new GridLayout(1,5));
        p.setPreferredSize(new Dimension(800, 50));

        // Order for elements to appear
        add(jtTitle);
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

    public ArrayList<Route> getRoutes() {
        return new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Back to loginscreen
        if (e.getSource() == jbLogout) {
            logout();
        }
        // "Start route" buttons are located in the class ButtonEditor
    }
    private void logout(){
        ActiveUser = null;
        this.dispose();
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);
    }

}
