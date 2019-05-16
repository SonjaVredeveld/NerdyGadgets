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
public class RouteLocation {
    private int number;
    private Order order;
    
    public RouteLocation(Order order) {
        this.order = order;
    }
    
    public RouteLocation(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID+"");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT OrderID, CustomerID FROM orders WHERE OrderID = ?", prepares);
        if(rows.size() == 1) {
            //saving the values from the database
            this.customer = new Customer(Integer.parseInt(rows.get(0).get(1)));
            this.ID = Integer.parseInt(rows.get(0).get(1));
        } else{
            System.out.println("test2");
        }
    }
    
    public Order getOrder() {
        return this.order;
    }
    
    public int getNumber() {
        return this.number;
    }
}
