/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import static java.lang.Boolean.FALSE;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Niek J Nijland
 */
class Route {
    private ArrayList<RouteLocation> routeLocations = new ArrayList<>();
    private int ID;
    private int driverID;
    
    public Route(ArrayList<Order> orderArray) {
        
        RouteLocation rl1;
        for(int i = 0;i < orderArray.size();i++) {
            routeLocations.add(new RouteLocation(orderArray.get(i)));
        }
        ArrayList<RouteLocation> optimalRoute = HK_extended.berekenPath(routeLocations);
        
        ArrayList<String> routePrepares = new ArrayList<>();
        ArrayList<String> routeLocationPrepares = new ArrayList<>();

        //getting the last ID + 1 from the databasetable
        int newRouteID = DBConnection.getNewId("routes", "RouteID");
        routePrepares.add(newRouteID+"");
        routePrepares.add(HK_extended.getOptimalDistance()+"");
        
        int resultRoute = DBConnection.insertQuery("INSERT INTO routes VALUES (?,NOW(),?,NULL)", routePrepares);
        
        if(resultRoute == 0) {
            //JOptionPane.showMessageDialog(,"Er ging iets fout bij het bereken van uw route");
        }else {
            int newRouteLocationID = DBConnection.getNewId("routelocation", "RouteLocationID");   
            String insertQuery = "INSERT INTO routelocation VALUES (?,?,?,?)";
        
            for(int i = 0;i< optimalRoute.size();i++) {
                if(i < optimalRoute.size() - 1) {
                    insertQuery = insertQuery + ", (?,?,?,?)";
                }
                routeLocationPrepares.add((newRouteLocationID + i)+"");
                routeLocationPrepares.add(optimalRoute.get(i).getOrder().getID()+"");
                routeLocationPrepares.add(newRouteID+"");
                routeLocationPrepares.add((i+1)+"");
            }
            
            int resultRouteLocation = DBConnection.insertQuery(insertQuery, routeLocationPrepares);
            
            if(resultRouteLocation == 0) {
                //JOptionPane.showMessageDialog(this,"Er ging iets fout bij het bereken van uw route");
                ArrayList<String> deleteRoutePrepares = new ArrayList<>();
                deleteRoutePrepares.add(newRouteID+"");
                DBConnection.insertQuery("DELETE FROM routes WHERE RouteID = ?", deleteRoutePrepares);
            }
        }
        
        
    }
    
    public Route(int ID) {
        
    }
    
    public boolean addDriver(int driverID) {
        return FALSE;
    }
    
    public ArrayList<RouteLocation> getLocations() {
        return this.routeLocations;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public ArrayList<Route> getRoutes() {
        return new ArrayList<>();
    }
}
