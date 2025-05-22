package algorithm;

import data_structures.AdjacencyList;

public class EdmondsKarp {

    private AdjacencyList graph;
    private int source;
    private int sink;
    private int maxFlow;

    /*
     * Constructor for the Algorithm.
     *
     * @param graph The AdjacencyList representing the Network.
     * @param source The Source Node.
     * @param sink The Sink Node.
     */
    public EdmondsKarp(AdjacencyList graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.maxFlow = 0; // Initially the flow is zero to start with.
    }

    public void runEdmondsKarp() {
        int numNodes = graph.getNumNodes();
        int[] parent = new int[numNodes];
    }
}
