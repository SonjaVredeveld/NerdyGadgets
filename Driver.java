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
public class Driver {
    private int PersonID;
    private String FullName;
    
    public Driver(int ID) {
        this.PersonID = ID;
        ArrayList prep1 = new ArrayList<>();
        prep1.add(ID+"");
        ArrayList<ArrayList<String>> rows1 = DBConnection.selectQuery("SELECT FullName FROM people WHERE PersonID = ?", prep1);
        if(rows1.size() > 0) {
            this.FullName = rows1.get(0).get(0);
        }
    }
    
    //TODO: filter drivers
    //return: ArrayList with all drivers
    public static ArrayList<Driver> getDrivers() {
        ArrayList driverList = new ArrayList<>();
        ArrayList<ArrayList<String>> rows1 = DBConnection.selectQuery("SELECT PersonID FROM people WHERE userRights = 'Driver'");
        for (int i = 0; i < rows1.size(); i++) {
            driverList.add(new Driver(Integer.parseInt(rows1.get(i).get(0))));
        }
        return driverList;
    }

    public String getFullName() {
        return this.FullName;
    }
    
    public int getID() {
        return this.PersonID;
    }
}
