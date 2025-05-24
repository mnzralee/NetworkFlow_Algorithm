package algorithm;

import data_structures.AdjacencyList;
import data_structures.Edge;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class EdmondsKarp {

    private AdjacencyList graph;
    private int source;
    private int sink;
    private int maxFlow;

    // a copy of the initial graph to display original capacities
    private AdjacencyList originalGraph;


    /**
     * Constructor for the EdmondsKarp algorithm.
     *
     * @param graph  The AdjacencyList representing the network.
     * @param source The source node.
     * @param sink   The sink node.
     */
    public EdmondsKarp(AdjacencyList graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.maxFlow = 0;

        // Deep copy the graph so we can modify 'this.graph' while keeping original for display/analysis
        this.originalGraph = new AdjacencyList(graph.getNumNodes());
        for (int i = 0; i < graph.getNumNodes(); i++) {
            for (Edge edge : graph.getNeighbors(i)) {
                this.originalGraph.addEdge(i, edge.getDestination(), edge.getCapacity());
            }
        }
    }

    /**
     * Executes the Edmonds-Karp algorithm to find the maximum flow
     * from the source to the sink in the given graph.
     * Also prints the augmenting paths and flow details
     */
    public void runEdmondsKarp() {
        int numNodes = graph.getNumNodes();
        int[] parent = new int[numNodes]; // Stores the augmenting path

        int iteration = 1;
        // Continue as long as there is an augmenting path from source to sink
        while (bfs(source, sink, parent)) {
            System.out.println("\n--- Augmentation Iteration " + iteration + " ---");

            // Find the bottleneck capacity along the augmenting path
            int pathFlow = Integer.MAX_VALUE;
            // Trace path backwards to find bottleneck
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, getCapacity(u, v));
            }

            // print the augmenting path and its bottleneck capacity
            printAugmentingPath(parent, source, sink, pathFlow);

            // Update the residual capacities along the augmenting path
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                updateCapacity(u, v, pathFlow);     // Decrease forward capacity
                updateCapacity(v, u, pathFlow);     // Increase backward capacity (for residual graph)
            }

            maxFlow += pathFlow; // Add the path flow to the total max flow
            System.out.println("Flow added in this iteration: " + pathFlow);
            System.out.println("Current total flow: " + maxFlow);
            iteration++;
        }
        System.out.println("\n--- Algorithm Finished ---");
        System.out.println("Maximum Flow from " + source + " to " + sink + ": " + maxFlow);
    }

    /**
     * Performs Breadth-First Search (BFS) to find an augmenting path
     * from the source to the sink in the residual graph.
     *
     * @param source The source node.
     * @param sink   The sink node.
     * @param parent An array to store the parent of each node in the augmenting path.
     * @return True if an augmenting path is found, false otherwise.
     */
    private boolean bfs(int source, int sink, int[] parent) {
        int numNodes = graph.getNumNodes();
        boolean[] visited = new boolean[numNodes];
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(parent, -1); // Initialize parent array with -1 (no parent)

        visited[source] = true;
        queue.add(source);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            List<Edge> neighbors = graph.getNeighbors(u);
            for (Edge edge : neighbors) {
                int v = edge.getDestination();
                int capacity = edge.getCapacity();

                if (!visited[v] && capacity > 0) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                    if (v == sink) { // Found a path to the sink
                        return true;
                    }
                }
            }
        }

        return false; // No augmenting path found
    }

    /**
     * Gets the capacity of the edge from node u to node v.
     * Handles the case where the edge doesn't exist or is a backward edge.
     *
     * @param u The source node.
     * @param v The destination node.
     * @return The capacity of the edge (0 if the edge doesn't exist).
     */
    private int getCapacity(int u, int v) {
        List<Edge> neighbors = graph.getNeighbors(u);
        if (neighbors != null) {
            for (Edge edge : neighbors) {
                if (edge.getDestination() == v) {
                    return edge.getCapacity();
                }
            }
        }
        return 0; // If the edge doesn't exist, return 0 capacity
    }

    /**
     * Updates the capacity of the edge from node u to node v.
     * If the edge doesn't exist, it adds a new edge (for backward edges in residual graph).
     *
     * @param u           The source node.
     * @param v           The destination node.
     * @param flowChange  The amount to change the capacity by.
     */
    private void updateCapacity(int u, int v, int flowChange) {
        List<Edge> neighbors = graph.getNeighbors(u);
        boolean edgeExists = false;
        if (neighbors != null) {
            for (Edge edge : neighbors) {
                if (edge.getDestination() == v) {
                    edge.setCapacity(edge.getCapacity() - flowChange);
                    edgeExists = true;
                    break;
                }
            }
        }
        if (!edgeExists) {
            graph.addEdge(u, v, flowChange); // Add backward edge
        }
    }

    /**
     * Returns the maximum flow calculated by the Edmonds-Karp algorithm.
     *
     * @return The maximum flow value.
     */
    public int getMaxFlow() {
        return maxFlow;
    }

    /**
     * Helper method to print the augmenting path in a readable format.
     * @param parent The parent array generated by BFS.
     * @param s The source node.
     * @param t The sink node.
     * @param pathFlow The bottleneck capacity of this path.
     */
    private void printAugmentingPath(int[] parent, int s, int t, int pathFlow) {
        Stack<Integer> path = new Stack<>();
        int curr = t;
        while (curr != -1) {
            path.push(curr);
            curr = parent[curr];
        }

        StringBuilder sb = new StringBuilder("Augmenting Path: ");
        while (!path.empty()) {
            sb.append(path.pop());
            if (!path.empty()) {
                sb.append(" -> ");
            }
        }
        sb.append(" (Bottleneck: ").append(pathFlow).append(")");
        System.out.println(sb.toString());
    }

//    public static void main(String[] args) {
//        // Example usage (replace with your actual graph and source/sink)
//        AdjacencyList graph = new AdjacencyList(6);
//        graph.addEdge(0, 1, 10);
//        graph.addEdge(0, 2, 5);
//        graph.addEdge(1, 3, 9);
//        graph.addEdge(2, 1, 3);
//        graph.addEdge(2, 3, 6);
//        graph.addEdge(3, 4, 10);
//        graph.addEdge(3, 5, 15);
//        graph.addEdge(4, 5, 10);
//
//        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, 0, 5);
//        edmondsKarp.runEdmondsKarp();
//        System.out.println("Max Flow: " + edmondsKarp.getMaxFlow()); // Output: 25
    //    }
    // The main method for testing the algorithm with the parser.
    public static void main(String[] args) {
        // Assume 'input.txt' is in the project root directory.
        // You might need to adjust the path if it's elsewhere.
        String filePath = "input.txt"; // Example file path

        try {
            // Parse the network from the file using your NetworkParser
            parser.NetworkParser parser = new parser.NetworkParser(); // Instantiate if not static, or just call static method
            AdjacencyList graph = parser.parseNetwork(filePath);

            int sourceNode = 0; // Example: Source node is 0
            int sinkNode = graph.getNumNodes() - 1; // Example: Sink node is the last node

            System.out.println("Initial Network Graph:");
            System.out.println(graph); // Print initial graph (optional but good for context)

            EdmondsKarp edmondsKarp = new EdmondsKarp(graph, sourceNode, sinkNode);
            edmondsKarp.runEdmondsKarp(); // Run the algorithm, which will print augmentation steps

            System.out.println("\nFinal Max Flow: " + edmondsKarp.getMaxFlow());

            // You could also print the residual graph here to see the final capacities,
            // but the coursework mainly asks for the steps.
             System.out.println("\nFinal Residual Graph:");
             System.out.println(graph);

        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found at " + filePath + ". " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}