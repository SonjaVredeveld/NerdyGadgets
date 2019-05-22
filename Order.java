/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.sql.Date;
import java.time.LocalDate;
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
        prepares.add(ID + "");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT OrderID, CustomerID FROM orders WHERE OrderID = ?", prepares);
        if (rows.size() == 1) {
            //saving the values from the database
            this.customer = new Customer(Integer.parseInt(rows.get(0).get(1)));
            this.ID = Integer.parseInt(rows.get(0).get(0));
        }
    }

    //return: ArrayList with all available Orders
    protected static ArrayList<Order> getOrders() {
        //getting all available OrderID's
        ArrayList<Order> orderList = new ArrayList<>();
        //Get Orders from database
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT o.OrderID FROM orders o JOIN Customers c ON o.CustomerID = c.CustomerID WHERE OrderID NOT IN (SELECT OrderID FROM routelocation) ORDER BY  o.OrderDate ASC , c.DeliveryPostalCode DESC LIMIT 50");
        if(0 < rows.size()) {

            //creating an order for every OrderID
            for (int i = 0; i < rows.size(); i++) {
                orderList.add(new Order(Integer.parseInt(rows.get(i).get(0))));
            }
        }
        return orderList;
    }

    //gets the needed order objects
    //param1: the date to filter the results on
    //return: ArrayList with all available Orders
    protected static ArrayList<Order> getOrders(LocalDate date) {
        //getting all available OrderID's
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<Object> prepares = new ArrayList<>();
        //get the correct dates to correctly filter
        LocalDate startMonth = date.minusMonths(1);
        startMonth = startMonth.withDayOfMonth(startMonth.lengthOfMonth());
        LocalDate endMonth = date.plusMonths(1);
        endMonth = endMonth.withDayOfMonth(1);

        prepares.add(Date.valueOf(startMonth));
        prepares.add(Date.valueOf(endMonth));

        //Get Orders from database
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT OrderID FROM orders o JOIN Customers c ON o.CustomerID = c.CustomerID WHERE OrderDate > ? && OrderDate < ? ORDER BY OrderDate", prepares, true);

        if (0 < rows.size()) {
            //creating an order for every OrderID
            for (int i = 0; i < rows.size(); i++) {
                orderList.add(new Order(Integer.parseInt(rows.get(i).get(0))));
            }
        }
        System.out.println(DBConnection.statusMsg);
        return orderList;
    }

    public int getID() {
        return this.ID;
    }

    public Customer getCustomer() {
        return this.customer;
    }
    
    public void ordersDelivered(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID+"");
        DBConnection.executeQuery("update orders set isDelivered = 1 where OrderID = ?", prepares);
    }

}
