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
import javax.swing.border.EmptyBorder;

public class EditCustomer extends JDialog implements ActionListener {

    private JTextField JTFfirstName, JTFlastName, JTFaddressLine, JTFcity;
    private JLabel JLfirstName, JLlastName, JLaddressLine, JLcity, JLtitle;
    private JButton JBsave, JBcancel;
    private JPanel panelCustomer, panelButtons;

    public EditCustomer(JFrame screen) {
        super(screen, true);
        setTitle("Verander Klantgegevens");
        setSize(300, 300);
        setLayout(new FlowLayout());

        panelCustomer = new JPanel();
        panelCustomer.setLayout(new GridLayout(4, 1, 10, 40));

        JLfirstName = new JLabel("Voornaam: ");
        panelCustomer.add(JLfirstName);

        JTFfirstName = new JTextField(25);
        panelCustomer.add(JTFfirstName);

        JLlastName = new JLabel("Achternaam:");
        panelCustomer.add(JLlastName);

        JTFlastName = new JTextField(25);
        panelCustomer.add(JTFlastName);

        JLaddressLine = new JLabel("Adres: ");
        panelCustomer.add(JLaddressLine);

        JTFaddressLine = new JTextField(25);
        panelCustomer.add(JTFaddressLine);

        JLcity = new JLabel("Woonplaats: ");
        panelCustomer.add(JLcity);

        JTFcity = new JTextField(25);
        panelCustomer.add(JTFcity);

//        add(panelCustomer);
        // CONFIRM OR CANCEL BUTTONS
        // TODO -- USE TO UPDATE DATABASE
//        panelButtons = new JPanel();
//        panelButtons.setLayout(new GridLayout(1,1,10,10));
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
        dispose();

    }

}
