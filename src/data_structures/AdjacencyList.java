package data_structures;

import java.util.ArrayList;
import java.util.List;

/*
 * Student ID: W2051634 | 20230327
 * Name      : Manazir Ali
 */

public class AdjacencyList {
    private final List<List<Edge>> adjList; // A list where each index represents a node, and the list at that index contains the edges originating from that node.
    private final int  numNodes;           // The total number of nodes in the network.

    /**
     * Constructor for the AdjacencyList class.
     * @param numNodes The number of nodes in the network.
     */
    public AdjacencyList(int numNodes) {
        this.numNodes = numNodes;
        this.adjList = new ArrayList<>(numNodes);
        // Initialize an empty list of edges for each node.
        for (int i = 0; i < numNodes; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    /**
     * Adds a directed edge to the adjacency list.
     * @param source The starting node of the edge.
     * @param destination The ending node of the edge.
     * @param capacity The capacity of the edge.
     */
    public void addEdge(int source, int destination, int capacity) {
        // store outgoing edges from each node.
        adjList.get(source).add(new Edge(destination, capacity));
    }

    /**
     * Returns a list of edges originating from a given node.
     * These represent the neighbors of the node and the capacities of the edges connecting to them.
     * @param node The node for which to retrieve the neighbors.
     * @return A List of Edge objects connected to the given node.
     */
    public List<Edge> getNeighbors(int node) {
        return adjList.get(node);
    }

    /**
     * Returns the total number of nodes in the network.
     * @return The number of nodes.
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Returns a string representation of the adjacency list.
     * @return A string showing each node and its outgoing edges.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numNodes; i++) {
            sb.append("Node ").append(i).append(": ").append(adjList.get(i)).append("\n");
        }
        return sb.toString();
    }
}