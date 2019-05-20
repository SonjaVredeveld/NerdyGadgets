/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditStock extends JDialog implements ActionListener {

    private JTextField JTFnewStock;
    private JLabel JLNewStock;
    private JLabel JLProductName;
    private JLabel JLCurrentStock;
    private JLabel JLStock;
    private JButton JBok = new JButton();
    private JButton JBcancel = new JButton();
    private JPanel panelP;
    private JPanel panelS;
    private Product product;

    public EditStock(JFrame screen, Product product) {
        super(screen, true);

        this.product = product;

        setTitle("Verander voorraad");
        setSize(300, 300);
        setLayout(new FlowLayout());

        // Panel for PRODUCTNAME
        panelP = new JPanel();
        panelP.setLayout(new GridLayout(1, 1, 20, 20));

        JLProductName = new JLabel("{{productnaam}}");
        panelP.add(JLProductName);

        add(panelP);

        // Panel for STOCKITEM info
        panelS = new JPanel();
        panelS.setLayout(new GridLayout(3, 3, 30, 30));

        JLCurrentStock = new JLabel("Huidige voorraad: ");
        panelS.add(JLCurrentStock);

        // TODO -- GET STOCK FROM DATABASE
        JLStock = new JLabel("{{voorraad}}");
        panelS.add(JLStock);

        JLNewStock = new JLabel("Nieuwe voorraad: ");
        panelS.add(JLNewStock);

        JTFnewStock = new JTextField(10);
        panelS.add(JTFnewStock);

        // TODO-- USE JTFnewStock TO UPDATE DATABASE
        JBok = new JButton("Ok");
        panelS.add(JBok);
        JBok.addActionListener(this);

        JBcancel = new JButton("cancel");
        panelS.add(JBcancel);
        JBcancel.addActionListener(this);
        add(panelS);
    }

    public void setNewStock(int newStock) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBcancel) {
            dispose();
        } else if (e.getSource() == JBok) {
            // TODO -- UPDATE DATABASE
            dispose();
        }
    }
}
