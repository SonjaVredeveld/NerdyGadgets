/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

/**
 *
 * @author Niek J Nijland
 */
public class RouteLocation {
    private int number;
    private Order order;
    
    public RouteLocation(Order order) {
        
    }
    
    public RouteLocation(int ID) {
        
    }
    
    public Order getOrder() {
        return this.order;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
}
