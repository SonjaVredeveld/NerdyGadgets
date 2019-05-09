/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Boolean.FALSE;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Niek J Nijland
 */
public class LoginScreen extends JFrame implements ActionListener{
    private JTextField JTFUsername = new JTextField();
    private JTextField JTFPassword = new JTextField();
    private JButton JBSubmit = new JButton();
    private JLabel JLInfoText = new JLabel();
    private JLabel JLlostPassword = new JLabel();
    
    public LoginScreen(){
        
    }
    
    public boolean login(String username,String password) {
        return FALSE;
    }
    
    public String getUsername() {
        return "username";
    }
    
    public String getPassword() {
        //hier moet dan iets met hashing komen
        return "encoded password";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
