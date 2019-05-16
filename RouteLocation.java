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
    private int Number;
    private Order order;
    
    public RouteLocation(Order order) {
        this.order = order;
    }
    
    public RouteLocation(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID+"");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT OrderID, RouteNumber FROM routelocation WHERE RouteLocationID = ?", prepares);
        if(rows.size() == 1) {
            this.order = new Order(Integer.parseInt(rows.get(0).get(0)));
            this.Number = Integer.parseInt(rows.get(0).get(1));
        }
    }
    
    public Order getOrder() {
        return this.order;
    }
    
    public int getNumber() {
        return this.Number;
    }
}
