/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import static java.lang.Boolean.FALSE;
import java.util.ArrayList;

/**
 *
 * @author Niek J Nijland
 */
class Route {
    private ArrayList<RouteLocation> routeLocations = new ArrayList<>();
    private int ID;
    private int driverID;
    
    public Route(ArrayList<Order> orderArray) {
        
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
