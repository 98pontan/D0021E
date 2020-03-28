package Sim;

import java.util.*;

/*
    Class to store the routing information based on Dijkstras algorithm. Class is shared between all the routers.
 */
public class LSDB {
    private int numberOfInterfaces;
    private DijkstrasAlgorithm dijkstrasAlgorithm;
    private int numberOfRouters;
    private int numberOfLinks;
    private int[][] routingMatrix;
    private int flag = 0; //Flag for when to run dijkstra
    private Map<Integer, ArrayList> optimalRoute = new HashMap<Integer, ArrayList>();
    private Set<Integer> linkedHashSet = new LinkedHashSet<>(); //delete?

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

    public void increaseNumberOfInterfaces() {
        numberOfInterfaces++;
        System.out.println("RINKU NUYMBER " + numberOfInterfaces);
    }

    public void addToMatrix(int sourceRouter, int destinationRouter, int value) {
        routingMatrix[sourceRouter-1][destinationRouter-1] = (int) Math.ceil(100.0/value); //Puts the routing paths and cost into an adjency matrix
        flag++;
        if(flag == numberOfInterfaces) { //Very hardcoded atm, supposes that everyrouter has equal amount of links
            for (int x = 0; x < numberOfRouters; x++) {
                dijkstrasAlgorithm = new DijkstrasAlgorithm(routingMatrix, x); //Runs dijkstras on the matrix
               // System.out.println(dijkstrasAlgorithm.getDijkstraTable());
                optimalRoute.put(x+1, dijkstrasAlgorithm.getDijkstraTable()); //Puts dijkstras result into a hashmap
            }
        }
    }

    public ArrayList<Integer> getRoute(int sourceRouter, int destRouter) {
        ArrayList<ArrayList> route = optimalRoute.get(sourceRouter);
        int x = 0;
        for (ArrayList i : route) {
            if((int) (i.get(i.size() - 1)) == destRouter-1) {
                System.out.println(route.get(x));
                return route.get(x);
            }
            x++;
        }
        return optimalRoute.get(sourceRouter);
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

