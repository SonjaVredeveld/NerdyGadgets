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
class Order {
    private int ID;
    private Customer customer;
    
    public Order(int ID) {
        
    }
    
    public ArrayList<Order> getOrders() {
        return new ArrayList<>();
    }
    
    public int getID() {
        return this.ID;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    
}
