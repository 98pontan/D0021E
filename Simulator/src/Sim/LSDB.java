package Sim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
    Class to store the routing information based on Dijkstras algorithm. Class is shared between all the routers.
 */
public class LSDB {
    DijkstrasAlgorithm dijkstrasAlgorithm;
    int numberOfRouters;
    int numberOfLinks;
    int[][] routingMatrix;
    int flag = 0; //Flag for when to run dijkstra
    Map<Integer, ArrayList> optimalRoute = new HashMap<Integer, ArrayList>();

    //Make non hardcoded pls!!!!!!!!!!
    LSDB(int numberOfRouters, int numberOfLinks) {
        this.numberOfRouters = numberOfRouters;
        this.numberOfLinks = numberOfLinks;
        int[][] routingMatrix = new int[numberOfRouters][numberOfRouters];
        this.routingMatrix = routingMatrix;
    }
    private int x=1; //delete

    //delete
    int[][] adjacencyMatrix = {
            { 0, 5, 6, 0},
            { 5, 0, 0, 7},
            { 6, 0, 0, 5},
            { 0, 7, 5, 0} };


    public void addToMatrix(int sourceRouter, int destinationRouter, int value) {
        routingMatrix[sourceRouter-1][destinationRouter-1] = (int) Math.ceil(100.0/value); //Puts the routing paths and cost into an adjency matrix
        flag++;
        if(flag == numberOfLinks*numberOfRouters) { //Very hardcoded atm, supposes that everyrouter has equal amount of links
            for (int x = 0; x < numberOfRouters; x++) {
                dijkstrasAlgorithm = new DijkstrasAlgorithm(routingMatrix, x); //Runs dijkstras on the matrix
                System.out.println(dijkstrasAlgorithm.getDijkstraTable());
                optimalRoute.put(x+1, dijkstrasAlgorithm.getDijkstraTable()); //Puts dijkstras result into a hashmap
            }
        }
    }

    //DijkstrasAlgorithm dijkstrasAlgorithm = new DijkstrasAlgorithm(adjacencyMatrix, 1);

    //Delete this function
    public void increaseX() {
        x++;
        System.out.println("X is " + x);
    }

    public void runDijkstras(int routerID) {
        dijkstrasAlgorithm = new DijkstrasAlgorithm(routingMatrix, routerID);
    }

    public void printMatrix() {
        System.out.println(Arrays.deepToString(routingMatrix)
                .replace("],","\n").replace(",","\t| ")
                .replaceAll("[\\[\\]]", " "));
    }
    
	public void cleanMatrix() {
    	for( int i = 0; i < routingMatrix.length; i++ )
    		   Arrays.fill(routingMatrix[i], 0);
    }
}

