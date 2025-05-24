package data_structures;

/*
 * Student ID: W2051634 | 20230327
 * Name      : Manazir Ali
 */

public class Edge {
    private final int destination; // The node this edge points to
    private int capacity;    // The maximum flow this edge can carry

    /**
     * Constructor for the Edge class.
     * @param destination The destination node of the edge.
     * @param capacity The capacity of the edge.
     */
    public Edge(int destination, int capacity) {
        this.destination = destination;
        this.capacity = capacity;
    }

    /**
     * Returns the destination node of this edge.
     * @return The destination node.
     */
    public int getDestination() {
        return destination;
    }

    /**
     * Returns the capacity of this edge.
     * @return The capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of this edge.
     * @param capacity The new capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns a string representation of the Edge object.
     * @return A string in the format "(destination, capacity)".
     */
    @Override
    public String toString() {
        return "(" + destination + ", " + capacity + ")";
    }
}