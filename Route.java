/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.util.*;

/**
 *
 * @author Niek J Nijland
 */
class Route {

    private ArrayList<RouteLocation> routeLocations = new ArrayList<>();
    private int ID;
    private int distance;
    private int driverID;
    private String creationDate;
    public int result1 = 1;

    //creates a new route with the given Orders using the 2 TSP algoritms, also saves the route with the routelocations to the database
    public Route(ArrayList<Order> orderArray) {

        for (int i = 0; i < orderArray.size(); i++) {
            routeLocations.add(new RouteLocation(orderArray.get(i)));
        }
        
        //if 17 or less locations selected, use primary algoritm, if more then 17 use the secundary alogoritm
        if(this.routeLocations.size() < 18) {
            this.routeLocations = HeuristicsExtended.calculatePath(this.routeLocations);
            this.distance = HeuristicsExtended.getOptimalDistance();
        }else{
            this.routeLocations = TSP_GA.calculatePath(routeLocations);
            this.distance = TSP_GA.getOptimalDistance();
        }

        ArrayList<String> routePrepares = new ArrayList<>();


        //getting the last ID + 1 from the databasetable
        ID = DBConnection.getNewId("routes", "RouteID");
        routePrepares.add(ID+"");
        routePrepares.add(distance+"");

        result1 = DBConnection.executeQuery("INSERT INTO routes VALUES (?,NOW(),?,NULL)", routePrepares);
        System.out.println(DBConnection.statusMsg);
        result1 = 1;
        if (result1 != 0) {
            //inserting all the routelocations
            int newRouteLocationID = DBConnection.getNewId("routelocation", "RouteLocationID");
            String insertQuery = "INSERT INTO routelocation VALUES (?,?,?,?)";
            ArrayList<String> routeLocationPrepares = new ArrayList<>();
            for (int i = 0; i < routeLocations.size(); i++) {
                if (i != 0) {
                    insertQuery = insertQuery + ", (?,?,?,?)";
                }
                newRouteLocationID++;
                routeLocationPrepares.add((newRouteLocationID) + "");
                routeLocationPrepares.add(routeLocations.get(i).getOrder().getID() + "");
                routeLocationPrepares.add(ID + "");
                routeLocationPrepares.add((i + 1) + "");
            }
            
            result1 = DBConnection.executeQuery(insertQuery, routeLocationPrepares);
            
            if (result1 == 0) {
                //deleting the just inserted route incase anything goes wrong inserting its locations
                //JOptionPane.showMessageDialog(this,"Er ging iets fout bij het bereken van uw route");
                ArrayList<String> deleteRoutePrepares = new ArrayList<>();
                deleteRoutePrepares.add(ID + "");
                DBConnection.executeQuery("DELETE FROM routes WHERE RouteID = ?", deleteRoutePrepares);
            }
        }

    }
    
    //parm1: routeID to retrieve from the database
    public Route(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID + "");
        
        //get the locations for this route and save them
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT RouteLocationID FROM routelocation WHERE RouteID = ? ORDER BY RouteNumber ASC", prepares);
        if (rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                routeLocations.add(new RouteLocation(Integer.parseInt(rows.get(i).get(0))));
            }
        }
        
        ArrayList<String> prepares2 = new ArrayList<>();
        prepares2.add(ID + "");
        ArrayList<ArrayList<String>> route = DBConnection.selectQuery("SELECT distanceKM, DriverID, CreationDate FROM routes WHERE RouteID = ? ", prepares2);
        if (route.size() == 1) {
            this.ID = ID;
            this.distance = Integer.parseInt(route.get(0).get(0));
            if(route.get(0).get(1) != null) {
                this.driverID = Integer.parseInt(route.get(0).get(1));
            } else{
                this.driverID = 0;
            }
            this.creationDate = route.get(0).get(2);
        }
    }

    //return:  1 = successfull, 0 = unsuccesfull
    public boolean addDriver(int driverID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(driverID + "");
        prepares.add(this.ID + "");

        int result = DBConnection.executeQuery("UPDATE routes SET DriverID = ? WHERE RouteID = ?", prepares);
        return result == 1;
    }

    public ArrayList<RouteLocation> getLocations() {
        return this.routeLocations;
    }

    public int getID() {
        return this.ID;
    }

    public int getDistance() {
        return distance;
    }
    
    public String getCreationDate() {
        return this.creationDate;
    }

    //used to check in PlannerScreen if insert was succesful
    public boolean getResult() {
        if (result1 > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Route> getRoutes() {
        //sql select for routes
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT RouteID FROM routes ORDER BY CreationDate");
        ArrayList<Route> newRows = new ArrayList<>();
        //go over all the results
        //create a route for each row.
        for(int i = 0; i < rows.size(); i++)
        {
            String id = (String) rows.get(i).get(0);
            int nummer = Integer.parseInt(id);
            Route route = new Route(nummer);
            newRows.add(route);
        }
        //add each rout to a empty arraylist
        //return the created list.
        return newRows;
    }
    
    public void deleteRow(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID+"");
        DBConnection.executeQuery("delete from routes where RouteID = ?", prepares);
    }
}
