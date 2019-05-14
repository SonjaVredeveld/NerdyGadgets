package main;

import javax.swing.*;
import kbs2.*;

public class Main {

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
         kbs2.LoginScreen LS = new kbs2.LoginScreen();

    }
}
