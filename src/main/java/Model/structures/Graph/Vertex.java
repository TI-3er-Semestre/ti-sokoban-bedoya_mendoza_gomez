package Model.structures.Graph;

import Model.structures.LinkedList;

public class Vertex<T extends Comparable<T>>
        implements Comparable<Vertex<T>> {

    private T data;
    private LinkedList<Edge<T>> edges;

    public Vertex(T data) {
        this.data = data;
        this.edges = new LinkedList<>();
    }

    public T getData() {
        return data;
    }

    public LinkedList<Edge<T>> getEdges() {
        return edges;
    }

    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }

    @Override
    public int compareTo(Vertex<T> other) {
        return this.data.compareTo(other.data);
    }
}