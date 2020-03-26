package Sim;

import java.util.ArrayList;

/*
    Class to store the routing information based on Dijkstras algorithm. Class is shared between all the routers.
 */
public class LSDB {
    int[][] adjacencyMatrix = {
            { 0, 5, 6, 0},
            { 5, 0, 0, 7},
            { 6, 0, 0, 5},
            { 0, 7, 5, 0} };
    DijkstrasAlgorithm a = new DijkstrasAlgorithm(adjacencyMatrix);
}

