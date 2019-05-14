/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.util.ArrayList;

/**
 *
 * @author Niek J Nijland
 */
public /*static*/ class DBConnection {
    //private Connection connection = new Connection();
    //private Host host  = new Host();
    private String Username;
    private String Password;
    private String statusMsg;
    
    public DBConnection() {
    
    }
    
    public ArrayList<String> selectQuery() {
        return new ArrayList<>();
    }
    
    public String insertQuery() {
        return new String();
    }
        
    public String updateQuery() {
        return new String();
    }
    
}
