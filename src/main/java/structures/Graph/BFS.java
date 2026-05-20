package structures.Graph;

import structures.Queue;
import structures.LinkedList;
import structures.Iterator;

public class BFS<T extends Comparable<T>> {

    public void traverse(AdjacencyListGraph<T> graph, T start) {

        Queue<Vertex<T>> queue = new Queue<>();
        LinkedList<T> visited = new LinkedList<>();

        Vertex<T> startVertex = findVertex(graph, start);

        if (startVertex == null) {
            return;
        }

        queue.enqueue(startVertex);
        visited.add(start);

        while (!queue.isEmpty()) {

            Vertex<T> current = queue.dequeue();

            System.out.println(current.getData());

            Iterator<Edge<T>> iterator =
                    current.getEdges().iterator();

            while (iterator.hasNext()) {

                Edge<T> edge = iterator.next();

                Vertex<T> neighbor = edge.getDestination();

                if (!contains(visited, neighbor.getData())) {

                    visited.add(neighbor.getData());

                    queue.enqueue(neighbor);
                }
            }
        }
    }

    private Vertex<T> findVertex(AdjacencyListGraph<T> graph, T data) {

        Iterator<Vertex<T>> iterator = graph.getVertices().iterator();

        while (iterator.hasNext()) {

            Vertex<T> vertex = iterator.next();

            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }

        return null;
    }

    private boolean contains(
            LinkedList<T> list,
            T data
    ) {

        Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()) {

            if (iterator.next().equals(data)) {
                return true;
            }
        }

        return false;
    }
}