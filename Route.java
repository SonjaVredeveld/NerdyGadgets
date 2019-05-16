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
class Route {

    private ArrayList<RouteLocation> routeLocations = new ArrayList<>();
    private int ID;
    private int distance;
    private int driverID;
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
        int newRouteID = DBConnection.getNewId("routes", "RouteID");
        routePrepares.add(newRouteID+"");
        routePrepares.add(HeuristicsExtended.getOptimalDistance()+"");
        

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
                    routeLocationPrepares.add(newRouteID + "");
                    routeLocationPrepares.add((i + 1) + "");
                }
            }

            result1 = DBConnection.executeQuery(insertQuery, routeLocationPrepares);

            if (result1 == 0) {
                //deleting the just inserted route incase anything goes wrong inserting its locations
                //JOptionPane.showMessageDialog(this,"Er ging iets fout bij het bereken van uw route");
                ArrayList<String> deleteRoutePrepares = new ArrayList<>();
                deleteRoutePrepares.add(newRouteID + "");
                DBConnection.executeQuery("DELETE FROM routes WHERE RouteID = ?", deleteRoutePrepares);
            }
        }

    }

    public Route(int ID) {
        ArrayList<String> prepares = new ArrayList<>();
        prepares.add(ID + "");

        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT RouteLocationID FROM routelocation WHERE RouteID = ? ORDER BY RouteNumber ASC", prepares);
        if (rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                routeLocations.add(new RouteLocation(Integer.parseInt(rows.get(i).get(0))));
            }
        }
        ArrayList<String> prepares2 = new ArrayList<>();
        prepares2.add(ID + "");
        ArrayList<ArrayList<String>> rows2 = DBConnection.selectQuery("SELECT distanceKM, DriverID FROM routes WHERE RouteID = ? ORDER BY RouteNumber ASC", prepares2);
        if (rows2.size() == 1) {
            this.ID = ID;
            this.distance = Integer.parseInt(rows.get(0).get(0));
            this.driverID = Integer.parseInt(rows.get(0).get(2));
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

    //used to check in PlannerScreen if insert was succesful
    public boolean getResult() {
        if (result1 > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Route> getRoutes() {
        return new ArrayList<>();
    }
}
