/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditCustomer extends JDialog implements ActionListener {

    private JTextField JTFName, JTFaddressLine, JTFcity;
    private JLabel JLName, JLaddressLine, JLcity, JLtitle;
    private JButton JBsave, JBcancel;
    private JPanel panelCustomer, panelButtons;
    private Customer customer;
    private AdministratorScreen admin;

    public EditCustomer(JFrame screen, Customer customer) {
        super(screen, true);

        this.customer = customer;

        setTitle("Klantgegevens bewerken");
        setSize(700, 400);
        setLayout(new FlowLayout());

        panelCustomer = new JPanel();
        panelCustomer.setLayout(new GridLayout(5, 1, 10, 40));

        JLName = new JLabel("Klantnaam: ");
        panelCustomer.add(JLName);

        JTFName = new JTextField(25);
        JTFName.setText(customer.getCustomerName());
        panelCustomer.add(JTFName);

        JLaddressLine = new JLabel("Adres: ");
        panelCustomer.add(JLaddressLine);

        JTFaddressLine = new JTextField(25);
        JTFaddressLine.setText(customer.getDeliveryAddressLine2());
        panelCustomer.add(JTFaddressLine);

        JLcity = new JLabel("Woonplaats: ");
        panelCustomer.add(JLcity);

        JTFcity = new JTextField(25);
        JTFcity.setText(customer.getPostalAddressLine2());
        panelCustomer.add(JTFcity);

        JBsave = new JButton("Opslaan");
        panelCustomer.add(JBsave);
        JBsave.addActionListener(this);

        JBcancel = new JButton("Annuleer");
        panelCustomer.add(JBcancel);
        JBcancel.addActionListener(this);

        panelCustomer.setBorder(new EmptyBorder(50, 10, 10, 10));

        add(panelCustomer);
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBcancel) {
            dispose();
        } else if (e.getSource() == JBsave) {
            try {
                String fullname = JTFName.getText();
                String adress = JTFaddressLine.getText();
                String city = JTFcity.getText();
                customer.setCustomer(fullname, adress, city);
                dispose();
            } catch (Exception nfe) {
                JOptionPane.showMessageDialog(this, "Voer de juiste gegevens in");
                EditCustomer editCustomerDialog = new EditCustomer(admin, customer);
                editCustomerDialog.setVisible(true);
            }

        }
    }
}
