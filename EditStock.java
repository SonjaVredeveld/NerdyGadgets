/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditStock extends JDialog implements ActionListener {

    private JTextField JTFnewStock;
    private JLabel JLNewStock;
    private JLabel JLProductName;
    private JLabel JLCurrentStock;
    private JLabel JLStock;
    private JButton JBok;
    private JButton JBcancel;
    private JPanel panelP;
    private JPanel panelS;
    private Product product;
    private AdministratorScreen admin;

    public EditStock(JFrame screen, Product product) {
        super(screen, true);

        this.product = product;

        setTitle("Verander voorraad");
        setSize(350, 250);
        setLayout(new FlowLayout());

        // Panel for PRODUCTNAME
        panelP = new JPanel();
        panelP.setLayout(new GridLayout(1, 1, 20, 20));

        JLProductName = new JLabel(product.getName());
        panelP.add(JLProductName);

        add(panelP);

        // Panel for STOCKITEM info
        panelS = new JPanel();
        panelS.setLayout(new GridLayout(3, 3, 30, 30));

        JLCurrentStock = new JLabel("Huidige voorraad: ");
        panelS.add(JLCurrentStock);

        // TODO -- GET STOCK FROM DATABASE
        JLStock = new JLabel(String.valueOf(product.getStock()));
        panelS.add(JLStock);

        JLNewStock = new JLabel("Nieuwe voorraad: ");
        panelS.add(JLNewStock);

        JTFnewStock = new JTextField(10);
        panelS.add(JTFnewStock);

        // TODO-- USE JTFnewStock TO UPDATE DATABASE
        JBok = style.button("Ok");
        panelS.add(JBok);
        JBok.addActionListener(this);

        JBcancel = style.button("Annuleren");
        panelS.add(JBcancel);
        JBcancel.addActionListener(this);
        add(panelS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBcancel) {
            dispose();
        } else if (e.getSource() == JBok) {
            try {
                int newStock = Integer.parseInt(JTFnewStock.getText());
                product.setStock(newStock);
                dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Voer een getal in");
            }
        }
    }
}
