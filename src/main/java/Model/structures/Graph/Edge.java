package Model.structures.Graph;

public class Edge<T extends Comparable<T>>
        implements Comparable<Edge<T>> {

    private Vertex<T> destination;
    private int weight;

    public Edge(Vertex<T> destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex<T> getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge<T> other) {
        return Integer.compare(this.weight, other.weight);
    }
}