/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Niek J Nijland
 */
public class EditCustomer extends JDialog implements ActionListener {
    private JTextField JTFfistName = new JTextField();
    private JTextField JTFlastName = new JTextField();
    private JTextField JTFaddressLine = new JTextField();
    private JTextField JTFcity = new JTextField();
    private JLabel JBfistName = new JLabel();
    private JLabel JBlastName = new JLabel();
    private JLabel JBaddressLine = new JLabel();
    private JLabel JBcity = new JLabel();
    private JButton JBsave = new JButton();
    
    public EditCustomer() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    
}
