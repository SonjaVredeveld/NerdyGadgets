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
public class Customer {
    private int ID;
    private String deliveryAddressLine2;
    private String deliveryPostalCode;
    private String customerName;
    private String customerCity;
    private int longitude;
    private int latitude;
    
    public Customer(int ID) {
        //c.CustomerName, c.longitude, c.latitude, c.DeliveryAddressLine2, c.DeliveryPostalCode FROM c.customers
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID+"");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT c.CustomerID, c.DeliveryAddressLine2, c.DeliveryPostalCode, c.CustomerName, ci.CityName, c.longitude, c.latitude FROM customers c JOIN cities ci ON c.DeliveryCityID = ci.CityID WHERE CustomerID = ?", prepares);
        if(rows.size() > 0) {
            this.ID = Integer.parseInt(rows.get(0).get(0));
            this.deliveryAddressLine2 = rows.get(0).get(1);
            this.deliveryPostalCode = rows.get(0).get(2);
            this.customerName = rows.get(0).get(3);
            this.customerCity = rows.get(0).get(4);
            this.longitude = Integer.parseInt(rows.get(0).get(5));
            this.latitude = Integer.parseInt(rows.get(0).get(6));
        } else{
            System.out.println("test3");
        }
    }
    
    public static ArrayList<Customer> getCustomers() {
        //getting all available OrderID's
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT CustomerID FROM customers", new ArrayList<>());
        if(rows.size() > 0) {
            //creating an order for every OrderID
            for (int i = 0; i < rows.size(); i++) {
                customerList.add(new Customer(Integer.parseInt(rows.get(i).get(0))));
            }
        }else{
            System.out.println("test4");
        }
        return customerList;
    }
    
    public int getID() {
        return this.ID;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public String getDeliveryAddressLine2() {
        return deliveryAddressLine2;
    }

    public String getDeliveryPostalCode() {
        return deliveryPostalCode;
    }

    
    public String getCustomerCity() {
        return customerCity;
    }
    
    public int getLatitude() {
        return latitude;
    }
    
    public int getLongitude() {
        return longitude;
    }
}
