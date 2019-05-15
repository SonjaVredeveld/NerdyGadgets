/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static javax.swing.text.html.HTML.Attribute.ID;

/**
 *
 * @author Niek J Nijland
 */
public class inserttest {
    public static void main(String[] args) {
        
        for(int i = 1; i < 1061;i++) {
            ArrayList<String> prepares = new ArrayList<>();
            prepares.add(randomGetal(100)+"");
            prepares.add(randomGetal(100)+"");
            prepares.add(i+"");

            int yn = DBConnection.insertQuery("UPDATE customers SET longitude = ?,latitude = ? WHERE CustomerID = ?", prepares);
        }
    }
    
    private static int randomGetal(int max) {
        int randomInt = ThreadLocalRandom.current().nextInt(0, max);
        return(randomInt);
    }
}


