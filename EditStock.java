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
public class EditStock extends JDialog implements ActionListener{
    private JTextField JTFnewStock = new JTextField();
    private JLabel JLnewStock = new JLabel();
    private JLabel JLproductName = new JLabel();
    private JLabel currentStock = new JLabel();
    private JButton JBok = new JButton();
    private JButton JBcancel = new JButton();
    
    public void setNewStock(int newStock) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
