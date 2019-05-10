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

public class DriverScreen extends JFrame implements ActionListener{
    //private JTable JTAvailableroutes = new JTable();
    //private JButton JBShowroute = new JButton();
    private ArrayList<Route> routeList = new ArrayList<>();
    private JTable jtRouteTable;
    private JLabel jtTitle;
    private JButton jbLogout;
    private JButton jbShowRoute;
    private ButtonEditor button;
    
    public DriverScreen() {
        setLayout(new FlowLayout());
        setTitle("Beschikbare Routes");
        setPreferredSize(new Dimension(800, 600));

        jtTitle = new JLabel("Te rijden routes");
        jtTitle.setPreferredSize(new Dimension(800, 25));
        jtTitle.setHorizontalAlignment(JLabel.CENTER);
        add(jtTitle);

        String[][] data = {
                //Voorbeeld data
                { "1", "22", "2402 km", "Start route" },
        };

        String[] columnNames = { "Route nummer", "Aantal locaties", "Afstand", "Bekijk route" };
                //Voorbeeld data

        jtRouteTable = new JTable(data, columnNames);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jtRouteTable.getModel());
        jtRouteTable.setRowSorter(sorter);
        JScrollPane tableSP = new JScrollPane(jtRouteTable);
        tableSP.setPreferredSize(new Dimension(800, 500));
        tableSP.setAlignmentX(LEFT_ALIGNMENT);
        add(tableSP);

        jbShowRoute = new JButton("Start route");
        jtRouteTable.getColumn("Bekijk route").setCellRenderer(new ButtonRenderer());
        jtRouteTable.getColumn("Bekijk route").setCellEditor(
                new ButtonEditor(new JCheckBox()));
        // Maakt van de column "Bekijk route" een knop (zie classes ButtonRenderer en ButtonEditor)

        jbLogout = new JButton("Log uit");
        jbLogout.setBackground(new Color(158, 188, 237));
        jbLogout.setForeground(Color.BLACK);
        jbLogout.setBounds(100,100,100,100);
        jbLogout.addActionListener(this);
        add(jbLogout);

        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public Route getRoute() {
        return new Route();
    }
    
    public ArrayList<Route> getRoutes() {
        return new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbLogout){
            //MOET EEN LOG OUT FUNCTIE KOMEN
        }
        //De start route knoppen worden in de klasse ButtonEditor afgehandeld
    }
    
}