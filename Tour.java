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
import java.util.Collections;

public class Tour{

    // Holds our tour of cities
    private ArrayList tour = new ArrayList<RouteLocation>();
    // Cache
    private double fitness = 0;
    private int distance = 0;
    
    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < TourManager.numberOfRouteLocations(); i++) {
            tour.add(null);
        }
    }
    
    public Tour(ArrayList tour){
        this.tour = tour;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int routelocationIndex = 0; routelocationIndex < TourManager.numberOfRouteLocations(); routelocationIndex++) {
          setRouteLocation(routelocationIndex, TourManager.getRouteLocation(routelocationIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    // Gets a city from the tour
    public RouteLocation getRouteLocation(int tourPosition) {
        return (RouteLocation)tour.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setRouteLocation(int tourPosition, RouteLocation routelocation) {
        tour.set(tourPosition, routelocation);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }
    
    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }
    
    // Gets the total distance of the tour
    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int routelocationIndex=0; routelocationIndex < tourSize(); routelocationIndex++) {
                // Get city we're travelling from
                RouteLocation fromRouteLocation = getRouteLocation(routelocationIndex);
                // City we're travelling to
                RouteLocation destinationRouteLocation;
                // Check we're not on our tour's last city, if we are set our 
                // tour's final destination city to our starting city
                if(routelocationIndex+1 < tourSize() - 1){
                    destinationRouteLocation = getRouteLocation(routelocationIndex+1);
                }
                else{
                    destinationRouteLocation = getRouteLocation(0);
                }
                // Get the distance between the two cities
                tourDistance += distanceTo(fromRouteLocation, destinationRouteLocation);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return tour.size();
    }
    
    // Check if the tour contains a city
    public boolean containsRouteLocation(RouteLocation routelocation){
        return tour.contains(routelocation);
    }
    
    
    public ArrayList<RouteLocation> getPath() {
        ArrayList<RouteLocation> ar1 = new ArrayList<>();
        for (int i = 0; i < tourSize(); i++) {
            ar1.add(getRouteLocation(i));
        }
        this.tour.clear();
        return ar1;
    }
    
        // Gets the distance to given city
    public double distanceTo(RouteLocation routelocation1, RouteLocation routelocation2){
        //System.out.println(routelocation1);
        //System.out.println(routelocation2); 
        int x1 = routelocation1.getOrder().getCustomer().getLatitude();
        int x2 = routelocation2.getOrder().getCustomer().getLatitude();
        int y1 = routelocation1.getOrder().getCustomer().getLongitude();
        int y2 = routelocation2.getOrder().getCustomer().getLongitude();
        int xDistance = Math.abs(x1 - x2);
        int yDistance = Math.abs(y1 - y2);
        double distance2 = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return 1;
    }

}
