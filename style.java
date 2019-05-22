/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Niek J Nijland
 */

//we use this final class with static methods to apply the same styling anywhere in the application with ease.
final public class style {
    
    //forces static use of this class
    private style() {
       
    }
    
    
    //returns a styled JButton
    public static JButton button(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(158, 188, 237));
        button.setForeground(Color.BLACK);
        return button;

  }
}
