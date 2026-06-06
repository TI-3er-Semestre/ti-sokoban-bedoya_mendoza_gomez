package Model.structures.Graph;

import Model.structures.LinkedList;
import Model.structures.Iterator;
import Model.structures.Queue;
import Model.structures.Stack;


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

    public LinkedList<T> bfs(T start) {

        Vertex<T> startVertex = findVertex(start);

        if (startVertex == null) {
            return null;
        }

        Queue<Vertex<T>> queue = new Queue<>();
        LinkedList<T> visited = new LinkedList<>();
        LinkedList<T> result = new LinkedList<>();

        queue.enqueue(startVertex);
        visited.add(startVertex.getData());

        while (queue.isEmpty() == false) {

            Vertex<T> current = queue.dequeue();

            result.add(current.getData());

            Iterator<Edge<T>> iterator = current.getEdges().iterator();

            while (iterator.hasNext()) {

                Edge<T> edge = iterator.next();

                Vertex<T> neighbor = edge.getDestination();

                if (visited.containsVisited(neighbor.getData()) == false) {

                    visited.add(neighbor.getData());

                    queue.enqueue(neighbor);
                }
            }
        }

        return result;
    }

    public LinkedList<T> dfs(T start) {

        Vertex<T> startVertex = findVertex(start);

        if (startVertex == null) {
            return null;
        }

        Stack<Vertex<T>> stack = new Stack<>();
        LinkedList<T> visited = new LinkedList<>();
        LinkedList<T> result = new LinkedList<>();

        stack.push(startVertex);

        while (stack.isEmpty() == false) {

            Vertex<T> current = stack.pop();

            if (visited.containsVisited(current.getData()) == false) {

                visited.add(current.getData());

                result.add(current.getData());

                Iterator<Edge<T>> iterator = current.getEdges().iterator();

                while (iterator.hasNext()) {

                    Edge<T> edge = iterator.next();

                    stack.push(edge.getDestination());
                }
            }
        }

        return result;
    }
}