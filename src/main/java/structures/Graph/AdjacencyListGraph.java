package structures.Graph;

import structures.LinkedList;
import structures.Iterator;

public class AdjacencyListGraph<T extends Comparable<T>> implements Graph<T> {

    private LinkedList<Vertex<T>> vertices;

    public AdjacencyListGraph() {
        vertices = new LinkedList<>();
    }

    @Override
    public void addVertex(T data) {

        if (!containsVertex(data)) {
            vertices.add(new Vertex<>(data));
        }
    }

    @Override
    public void addEdge(T source, T destination, int weight) {

        Vertex<T> sourceVertex = findVertex(source);
        Vertex<T> destinationVertex = findVertex(destination);

        if (sourceVertex != null && destinationVertex != null) {

            Edge<T> edge = new Edge<>(destinationVertex, weight);

            sourceVertex.addEdge(edge);
        }
    }

    @Override
    public boolean containsVertex(T data) {

        return findVertex(data) != null;
    }

    @Override
    public boolean containsEdge(T source, T destination) {

        Vertex<T> sourceVertex = findVertex(source);

        if (sourceVertex == null) {
            return false;
        }

        Iterator<Edge<T>> iterator = sourceVertex.getEdges().iterator();

        while (iterator.hasNext()) {

            Edge<T> edge = iterator.next();

            if (edge.getDestination().getData().equals(destination)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void removeVertex(T data) {

        // opcional por ahora
    }

    @Override
    public void removeEdge(T source, T destination) {

        // opcional por ahora
    }

    private Vertex<T> findVertex(T data) {

        Iterator<Vertex<T>> iterator = vertices.iterator();

        while (iterator.hasNext()) {

            Vertex<T> vertex = iterator.next();

            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }

        return null;
    }

    public LinkedList<Vertex<T>> getVertices() {
        return vertices;
    }
}