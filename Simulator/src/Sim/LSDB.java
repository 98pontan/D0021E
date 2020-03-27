package Sim;

import java.util.ArrayList;
import java.util.Arrays;

/*
    Class to store the routing information based on Dijkstras algorithm. Class is shared between all the routers.
 */
public class LSDB {
    //int numberOfRouters;
    int[][] routingMatrix;
    LSDB(int numberOfRouters) {
        //this.numberOfRouters = numberOfRouters;
        int[][] routingMatrix = new int[numberOfRouters][numberOfRouters];
        this.routingMatrix = routingMatrix;
    }
    private int x=1;
    int[][] adjacencyMatrix = {
            { 0, 5, 6, 0},
            { 5, 0, 0, 7},
            { 6, 0, 0, 5},
            { 0, 7, 5, 0} };


    public void addToMatrix(int sourceRouter, int destinationRouter, int value) {
        routingMatrix[sourceRouter-1][destinationRouter-1] = value;
    }

    DijkstrasAlgorithm a = new DijkstrasAlgorithm(adjacencyMatrix);
    public void increaseX() {
        x++;
        System.out.println("X is " + x);
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

