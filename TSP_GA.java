/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.util.ArrayList;

/**
 *
 * @author Niek J Nijland && http://www.theprojectspot.com/tutorial-post/applying-a-genetic-algorithm-to-the-travelling-salesman-problem/5
 */
public class TSP_GA {
    private static int optimalDistance;

    public static ArrayList<RouteLocation> calculatePath(ArrayList<RouteLocation> ar1) {
        ArrayList<ArrayList<RouteLocation>> ar2 = new ArrayList<>();
        ArrayList<Integer> ar3 = new ArrayList<>();
        
        //calculating the route 10 times and saving all the paths and distances in ar2 and ar3, because of the randomizer
        for (int i = 0; i < 10; i++) {
            //feeding all the RouteLocations to the algoritm
            for (int i2 = 0; i2 < ar1.size(); i2++) {
                TourManager.addRouteLocation(ar1.get(i2));
            }
           
            // Evolve population for 100 generations, because of the mutating algoritm
            Population pop = new Population(50, true);
            pop = GeneticAlgoritm.evolvePopulation(pop);
            for (int i3 = 0; i3 < 100; i3++) {
                pop = GeneticAlgoritm.evolvePopulation(pop);
            }
            
            //saving the results
            ar2.add(pop.getFittest().getPath());
            ar3.add(pop.getFittest().getDistance());
            TourManager.clearArray();
        }
        
        //checking which run was the best
        int lowestDistanceIndex = -1;
        int lowestDistance = -1;
        for (int i4 = 0; i4 < ar3.size(); i4++) {
            if(ar3.get(i4) < lowestDistance) {
                lowestDistanceIndex = i4;
            }else if(lowestDistance == -1){
                lowestDistanceIndex = i4;
            }
        }
        
        //returning the best results
        optimalDistance = ar3.get(lowestDistanceIndex);
        return ar2.get(lowestDistanceIndex);
    }
    
    //return: best distance
    public static int getOptimalDistance() {
        int distance = TSP_GA.optimalDistance;
        TSP_GA.optimalDistance = 0;
        return distance;
    }
}
