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
public class Product {

    private int ID;
    private int stock;
    private String name;
    private float pricePerPiece;

    //initialize product
    public Product(int ID) {
        //get the needed product data.
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID + "");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT StockItemName, StockItems.StockItemID, QuantityOnHand, UnitPrice FROM stockitems as si JOIN stockiemholdings as sih ON si.StockItemID = sihStockItemID", prepares);

        //save the needed data
        System.out.println(rows);
        if (!rows.isEmpty()) {
            if (rows.size() > 0) {

                ArrayList<String> row = rows.get(0);
                try {
                    this.ID = Integer.parseInt(row.get(1));
                    this.stock = Integer.parseInt(row.get(2));
                    this.name = row.get(0);
                    this.pricePerPiece = Integer.parseInt(row.get(3));

                } catch (NumberFormatException ex) {

                }
            }
        }
    }

    public boolean setStock(int newStock) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(stock + "");
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

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        //get all the products
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT StockItemID FROM stockitems");
        if (!rows.get(0).isEmpty()) {
            for (int i = 0; i < rows.size(); i++) {
                Product product = new Product(Integer.parseInt(rows.get(0).get(0)));
                products.add(product);
            }
        } else {
            System.out.println(DBConnection.statusMsg);
        }
        return products;

    }

}
