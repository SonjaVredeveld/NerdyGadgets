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
    
    //parm: OrderID of the order you wish to create an object of
    public Order(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID+"");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT OrderID, CustomerID FROM orders WHERE OrderID = ?", prepares);
        if(rows.size() == 1) {
            //saving the values from the database
            this.customer = new Customer(Integer.parseInt(rows.get(0).get(1)));
            this.ID = Integer.parseInt(rows.get(0).get(0));
        } else{
            System.out.println("test2");
        }
    }
    
    //return: ArrayList with all available Orders
    public static ArrayList<Order> getOrders() {
        //getting all available OrderID's
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<String> prep2 = new ArrayList<>();
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT OrderID FROM orders WHERE isDelivered IS NULL ORDER BY OrderDate ASC LIMIT 50", prep2);
        if(0 < rows.size()) {
            //creating an order for every OrderID
            for (int i = 0; i < rows.size(); i++) {
                orderList.add(new Order(Integer.parseInt(rows.get(i).get(0))));
            }
        }else{
            System.out.println("test");
        }
        return orderList;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    
}
