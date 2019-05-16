/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * https://github.com/Sinclert/Heuristics-TSP extended by Niek Nijland
 */
public class HK_extended {

    /**
     *
     * @param p1
     * @param p2
     * @return
     */
    private static int[][] distances;
    private static int optimalDistance = Integer.MAX_VALUE;
    private static int optimalDistanceToReturn = 0;
    private static String optimalPath = "";
    
    private static int calculateDistance(RouteLocation rl1, RouteLocation rl2) {
        int x1 = rl1.getOrder().getCustomer().getLatitude();
        int x2 = rl2.getOrder().getCustomer().getLatitude();
        int y1 = rl1.getOrder().getCustomer().getLongitude();
        int y2 = rl2.getOrder().getCustomer().getLongitude();
        int vx = berekenVerschil(x1, x2);
        int vy = calculateDifference(y1, y2);
        if(vx == 0) {
            return vy;
        }else if(vy == 0){
            return vx;
        }else{
            return (int) sqrt((vx * vx) + (vy * vy));
        }
    }
    
    private static int calculateDifference(int c1, int c2) {
        if(c1 > c2) {
            return c1 - c2;
        }else if(c1 < c2) {
            return c2 - c1;
        }else{
            return 0;
        }
    }
    
    private static int[][] getDistance(ArrayList<RouteLocation> ar1) {
        int[][] afstandenArray = new int[ar1.size()][ar1.size()];
        for(int i = 0; i < ar1.size(); i++) {
            for(int j = 0; j < ar1.size(); j++) {
                afstandenArray[i][j] = berekenAfstand(ar1.get(i),ar1.get(j));
            }
        }
        return distancesArray;
    }
    
    /**
     *
     * @param ar1: ArrayList with locations that have to be visited in the route
     * @return ArrayList<RouteLocation>: ar2 sorted to the optimal route
     */
    public static ArrayList<RouteLocation> calculatePath(ArrayList<RouteLocation> ar1){

        // Distance matrix is set by our extension
        distances = getAfstanden(ar1);
        
        /*
        for (int[] distance : distances) {
            for (int distanc : distance) {
                System.out.print(distanc+"" + " ");
        }
            System.out.println("");
        } */

        // Initial variables to start the algorithm
        // because the function procedure is recursive we have to set these here
        String path = "";
        int[] vertices = new int[ar1.size() - 1];

        // Filling the initial vertices array with the proper values
        for (int i = 1; i < ar1.size(); i++) {
            vertices[i - 1] = i;
        }

        // FIRST CALL TO THE RECURSIVE FUNCTION
        procedure(0, vertices, path, 0);

        String[] pathArr = optimalPath.split("-");
        ArrayList<RouteLocation> ar2 = new ArrayList<>();
        for(String a:pathArr) {
            ar2.add(ar1.get(Integer.valueOf(a)));
        }
        optimalDistanceToReturn = optimalDistance;
        // these variables are decleared at class-level so that the function procedure can reach them aswell, this works fine with a single run,
        // but to us this means that they stay saved in memory even after the calculation is done. this causes some problems within our application and
        // that is why we reset them here
        distances = null;
        vertices = null;
        optimalPath = null;
        optimalDistance = Integer.MAX_VALUE;
        System.gc();
        
        System.out.println("nu zou de boel berekend moeten zijn");
        return ar2;
    }
    
    /* https://github.com/Sinclert/Heuristics-TSP */
    //every route starts at the first location in the array, we can change that later if we have time left
    private static int procedure(int initial, int vertices[], String path, int costUntilHere) {

        // We concatenate the current path and the vertex taken as initial
        path = path + Integer.toString(initial) + "-";
        int length = vertices.length;
        int newCostUntilHere;


        // Exit case, if there are no more options to evaluate (last node)
        if (length == 0) {
            newCostUntilHere = costUntilHere + distances[initial][0];

            // If its cost is lower than the stored one
            if (newCostUntilHere < optimalDistance){
                optimalDistance = newCostUntilHere;
                optimalPath = path + "0";
            }

            return (distances[initial][0]);
        }


        // If the current branch has higher cost than the stored one: stop traversing
        else if (costUntilHere > optimalDistance){
            return 0;
        }


        // Common case, when there are several nodes in the list
        else {

            int[][] newVertices = new int[length][(length - 1)];
            int costCurrentNode, costChild;
            int bestCost = Integer.MAX_VALUE;

            // For each of the nodes of the list
            for (int i = 0; i < length; i++) {

                // Each recursion new vertices list is constructed
                for (int j = 0, k = 0; j < length; j++, k++) {

                    // The current child is not stored in the new vertices array
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newVertices[i][k] = vertices[j];
                }

                // Cost of arriving the current node from its parent
                costCurrentNode = distances[initial][vertices[i]];

                // Here the cost to be passed to the recursive function is computed
                newCostUntilHere = costCurrentNode + costUntilHere;

                // RECURSIVE CALLS TO THE FUNCTION IN ORDER TO COMPUTE THE COSTS
                costChild = procedure(vertices[i], newVertices[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                int totalCost = costChild + costCurrentNode;

                // Finally we select from the minimum from all possible children costs
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            return (bestCost);
        }
    }
    
    public static int getOptimalDistance() {
        return optimalDistanceToReturn;
    }
}
