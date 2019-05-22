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
public class Customer extends getCoordinates {
    private getCoordinates coords = new getCoordinates();
    private String[] co;
    private int ID;
    private String deliveryAddressLine2;
    private String deliveryPostalCode;
    private String customerName;
    private String customerCity;
    private int longitude;
    private int latitude;

    public Customer(int ID) {
        //initializing customer from database
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID + "");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT c.CustomerID, c.DeliveryAddressLine2, c.DeliveryPostalCode, c.CustomerName, ci.CityName, c.longitude, c.latitude FROM customers c JOIN cities ci ON c.DeliveryCityID = ci.CityID WHERE CustomerID = ?", prepares);

        if(rows.size() > 0) {
            this.co = coords.getAddress(); //adds the longitude and latitude of items where longitude is null
            this.ID = Integer.parseInt(rows.get(0).get(0));
            this.deliveryAddressLine2 = rows.get(0).get(1);
            this.deliveryPostalCode = rows.get(0).get(2);
            this.customerName = rows.get(0).get(3);
            this.customerCity = rows.get(0).get(4);
            this.longitude = Integer.parseInt(rows.get(0).get(5));
            this.latitude = Integer.parseInt(rows.get(0).get(6));
        }
    }

    public static ArrayList<Customer> getCustomers() {
        //getting all available CustomerID's
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT CustomerID FROM customers");
        if (rows.size() > 0) {
            //creating a customer for every CustomerID
            for (int i = 0; i < rows.size(); i++) {
                try {
                    customerList.add(new Customer(Integer.parseInt(rows.get(i).get(0))));

                } catch (NumberFormatException ex) {
                    System.out.println(DBConnection.statusMsg);
                }
            }
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

    public boolean setStock(int newStock) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID + "");
        int rs = DBConnection.executeQuery("ALTER TABLE stockitemholdings SET QuantityOnHand = ? WHERE StockItemID = ?", prepares);
        if (rs > 0) {
            System.out.println("updated!");
            return true;
        } else {
            System.out.println(DBConnection.statusMsg);
            return false;
        }
    }
}
