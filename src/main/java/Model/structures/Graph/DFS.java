package Model.structures.Graph;

import Model.structures.Stack;
import Model.structures.LinkedList;
import Model.structures.Iterator;

public class DFS<T extends Comparable<T>> {

    public void traverse(AdjacencyListGraph<T> graph, T start) {

        Stack<Vertex<T>> stack = new Stack<>();
        LinkedList<T> visited = new LinkedList<>();

        Vertex<T> startVertex = findVertex(graph, start);

        if (startVertex == null) {
            return;
        }

        stack.push(startVertex);

        while (!stack.isEmpty()) {

            Vertex<T> current = stack.pop();

            if (!contains(visited, current.getData())) {

                visited.add(current.getData());

                System.out.println(current.getData());

                Iterator<Edge<T>> iterator =
                        current.getEdges().iterator();

                while (iterator.hasNext()) {

                    Edge<T> edge = iterator.next();

                    stack.push(edge.getDestination());
                }
            }
        }
    }

    private Vertex<T> findVertex(
            AdjacencyListGraph<T> graph,
            T data
    ) {

        Iterator<Vertex<T>> iterator =
                graph.getVertices().iterator();

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