package com.company;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DriverScreen extends JFrame implements ActionListener {
    private JTable jtRouteTable;
    private JLabel jtTitle;
    private JButton jbLogout;
    private JButton jbShowRoute;
    private ButtonEditor button;


    public DriverScreen(){
        setLayout(new FlowLayout());
        setTitle("Beschikbare Routes");
        setPreferredSize(new Dimension(800, 600));

        jtTitle = new JLabel("Te rijden routes");
        jtTitle.setPreferredSize(new Dimension(800, 25));
        jtTitle.setHorizontalAlignment(JLabel.CENTER);
        add(jtTitle);

        String[][] data = {
                { "1", "22", "2402 km", "Start route" },
                { "2", "12", "1255 km", "Start route" },
                { "3", "12", "1255 km", "Start route" },
                { "4", "12", "1255 km", "Start route" },
                { "5", "12", "1255 km", "Start route" },
                { "6", "12", "1255 km", "Start route" },
                { "7", "12", "1255 km", "Start route" },
                { "8", "22", "2402 km", "Start route" },
                { "9", "12", "1255 km", "Start route" },
                { "10", "12", "1255 km", "Start route" },
                { "11", "12", "1255 km", "Start route" },
                { "12", "12", "1255 km", "Start route" },
                { "13", "12", "1255 km", "Start route" },
                { "14", "12", "1255 km", "Start route" },
                { "15", "22", "2402 km", "Start route" },
                { "16", "12", "1255 km", "Start route" },
                { "17", "12", "1255 km", "Start route" },
                { "18", "12", "1255 km", "Start route" },
                { "19", "12", "1255 km", "Start route" },
                { "20", "12", "1255 km", "Start route" },
                { "21", "12", "1255 km", "Start route" },
                { "22", "22", "2402 km", "Start route" },
                { "23", "12", "1255 km", "Start route" },
                { "24", "12", "1255 km", "Start route" },
                { "25", "12", "1255 km", "Start route" },
                { "26", "12", "1255 km", "Start route" },
                { "27", "12", "1255 km", "Start route" },
                { "28", "12", "1255 km", "Start route" },
                { "29", "22", "2402 km", "Start route" },
                { "30", "12", "1255 km", "Start route" },
                { "31", "12", "1255 km", "Start route" },
                { "32", "12", "1255 km", "Start route" },
                { "33", "12", "1255 km", "Start route" },
                { "34", "12", "1255 km", "Start route" },
                { "35", "12", "1255 km", "Start route" },
                { "36", "22", "2402 km", "Start route" },
                { "37", "12", "1255 km", "Start route" },
                { "38", "12", "1255 km", "Start route" },
                { "39", "12", "1255 km", "Start route" },
                { "40", "12", "1255 km", "Start route" },
                { "41", "12", "1255 km", "Start route" },
                { "42", "12", "1255 km", "Start route" },
                { "43", "22", "2402 km", "Start route" },
                { "44", "12", "1255 km", "Start route" },
                { "45", "12", "1255 km", "Start route" },
                { "46", "12", "1255 km", "Start route" },
                { "47", "12", "1255 km", "Start route" },
                { "48", "12", "1255 km", "Start route" },
                { "49", "12", "1255 km", "Start route" },
                { "50", "22", "2402 km", "Start route" },
                { "51", "12", "1255 km", "Start route" },
                { "52", "12", "1255 km", "Start route" },
                { "53", "12", "1255 km", "Start route" },
                { "54", "12", "1255 km", "Start route" },
                { "55", "12", "1255 km", "Start route" },
                { "56", "12", "1255 km", "Start route" },
        };

        String[] columnNames = { "Route nummer", "Aantal locaties", "Afstand", "Bekijk route" };

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


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbLogout){
            //MOET EEN LOG OUT FUNCTIE KOMEN
        }
            //De start route knoppen worden in de klasse ButtonEditor afgehandeld
        }

    }