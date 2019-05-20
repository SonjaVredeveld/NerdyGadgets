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
    public int result1;

    //creates a new route with the given Orders using the TSP algorithm, also saves the route with the routelocations to the database
    public Route(ArrayList<Order> orderArray) {

        RouteLocation rl1;
        for (int i = 0; i < orderArray.size(); i++) {
            routeLocations.add(new RouteLocation(orderArray.get(i)));
        }
        routeLocations = HeuristicsExtended.calculatePath(routeLocations);
        

        ArrayList<String> routePrepares = new ArrayList<>();
        ArrayList<String> routeLocationPrepares = new ArrayList<>();

        //getting the last ID + 1 from the databasetable
        ID = DBConnection.getNewId("routes", "RouteID");
        routePrepares.add(ID+"");
        this.distance = HeuristicsExtended.getOptimalDistance();
        routePrepares.add(distance+"");

        result1 = DBConnection.executeQuery("INSERT INTO routes VALUES (?,NOW(),?,NULL)", routePrepares);

        if (result1 != 0) {
            //inserting all the routelocations
            int newRouteLocationID = DBConnection.getNewId("routelocation", "RouteLocationID");
            String insertQuery = "INSERT INTO routelocation VALUES (?,?,?,?)";

            for (int i = 0; i < routeLocations.size(); i++) {
                if (i != 0) {
                    if (i != 1) {
                        insertQuery = insertQuery + ", (?,?,?,?)";
                    }
                    routeLocationPrepares.add((newRouteLocationID + i) + "");
                    routeLocationPrepares.add(routeLocations.get(i).getOrder().getID() + "");
                    routeLocationPrepares.add(ID + "");
                    routeLocationPrepares.add((i + 1) + "");
                }
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
        
        //get route data and create instnciate object
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

    //return: 0 = allready a driver assigned, 1 = succes
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
        //check for empty result set
        ArrayList<Route> newRows = new ArrayList<>();
        //System.out.println(rows);
        System.out.println(DBConnection.statusMsg);
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
