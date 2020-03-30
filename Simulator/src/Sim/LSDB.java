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

    LSDB(int numberOfRouters, int numberOfLinks) {
        this.numberOfRouters = numberOfRouters;
        this.numberOfLinks = numberOfLinks;
        int[][] routingMatrix = new int[numberOfRouters][numberOfRouters];
        this.routingMatrix = routingMatrix;
    }

    public void increaseNumberOfInterfaces() {
        numberOfInterfaces++;
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
            System.out.println("Dijkstras has run and updated the LSDB table");
            printMatrix();
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

    public void printMatrix() {
        System.out.println(Arrays.deepToString(routingMatrix)
                .replace("],","\n").replace(",","\t| ")
                .replaceAll("[\\[\\]]", " "));
    }


    /*
        This function is saved for future use
     */
	public void cleanMatrix() {
    	for( int i = 0; i < routingMatrix.length; i++ )
    		   Arrays.fill(routingMatrix[i], 0);
    }
}

