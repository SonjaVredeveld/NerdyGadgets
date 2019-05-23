/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

/**
 *
 * @author http://www.theprojectspot.com/tutorial-post/applying-a-genetic-algorithm-to-the-travelling-salesman-problem/5
 */
import java.util.ArrayList;

public class TourManager {

    // Holds our cities
    private static ArrayList destinationRouteLocations = new ArrayList<RouteLocation>();

    // Adds a destination city
    public static void addRouteLocation(RouteLocation routelocation) {
        destinationRouteLocations.add(routelocation);
    }
    
    public static void overwriteRouteLocationList(ArrayList<RouteLocation> a) {
        destinationRouteLocations = a;
    }
    
    // Get a city
    public static RouteLocation getRouteLocation(int index){
        return (RouteLocation)destinationRouteLocations.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfRouteLocations(){
        return destinationRouteLocations.size();
    }
    
    public static void clearArray() {
        destinationRouteLocations.clear();
    }
}
