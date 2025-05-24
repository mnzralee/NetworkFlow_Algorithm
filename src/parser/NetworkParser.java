package parser;

import data_structures.AdjacencyList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Student ID: W2051634 | 20230327
 * Name      : Manazir Ali
 */

public class NetworkParser {

    /**
     * Parses the network description from the given input file and creates an AdjacencyList representation of the network.
     *
     * @param filePath The path to the input file.
     * @return An AdjacencyList representing the network.
     * @throws FileNotFoundException If the input file is not found.
     */
    public static AdjacencyList parseNetwork(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        // Read the number of nodes from the first line.
        int numNodes = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Create the AdjacencyList.
        AdjacencyList adjList = new AdjacencyList(numNodes);

        // Read the edges from the remaining lines.
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length == 3) {
                try {
                    int source = Integer.parseInt(parts[0]);
                    int destination = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);
                    adjList.addEdge(source, destination, capacity);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing edge: " + line + ". Skipping line.");
                }
            } else {
                System.err.println("Invalid edge format: " + line + ". Skipping line.");
            }
        }

        scanner.close();
        return adjList;
    }

    public static void main(String[] args) {
        try {
            AdjacencyList graph = parseNetwork("input.txt");
            System.out.println(graph); // Print the graph (for testing)
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        }
    }
}