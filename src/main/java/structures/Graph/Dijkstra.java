package structures.Graph;

import structures.PriorityQueue;
import structures.LinkedList;
import structures.Iterator;

/**
 * implementacion dijkstra que busca el camino mas corto desde un nodo inicial
 */
public class Dijkstra<T extends Comparable<T>> {

    /**
     * clase auxiliar para la cola de prioridad
     */
    public class NodeDistance implements Comparable<NodeDistance> {
        public Vertex<T> vertex;
        public int distance;

        public NodeDistance(Vertex<T> vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    /**
     * guardar las distancias finales
     */
    public class PathResult implements Comparable<PathResult> {
        public T data;
        public int distance;

        public PathResult(T data, int distance) {
            this.data = data;
            this.distance = distance;
        }

        @Override
        public int compareTo(PathResult other) {
            return this.data.compareTo(other.data);
        }
    }

    public LinkedList<PathResult> shortestpaths(AdjacencyListGraph<T> graph, T start) {
        LinkedList<PathResult> results = new LinkedList<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(1000);
        LinkedList<T> visited = new LinkedList<>();

        Vertex<T> startVertex = findvertex(graph, start);
        if (startVertex == null) {
            return results;
        }


        Iterator<Vertex<T>> it = graph.getVertices().iterator();
        while (it.hasNext()) {
            Vertex<T> v = it.next();
            if (v.getData().equals(start)) {
                results.add(new PathResult(v.getData(), 0));
                queue.enqueue(new NodeDistance(v, 0));
            } else {
                results.add(new PathResult(v.getData(), 999999));
            }
        }


        while (!queue.isEmpty()) {
            NodeDistance current = queue.dequeue();
            Vertex<T> u = current.vertex;

            if (contains(visited, u.getData())) {
                continue;
            }
            visited.add(u.getData());

            Iterator<Edge<T>> edgeIt = u.getEdges().iterator();
            while (edgeIt.hasNext()) {
                Edge<T> edge = edgeIt.next();
                Vertex<T> v = edge.getDestination();
                int weight = edge.getWeight();

                int uDist = getdistance(results, u.getData());
                int vDist = getdistance(results, v.getData());

                if (uDist + weight < vDist) {
                    updatedistance(results, v.getData(), uDist + weight);
                    queue.enqueue(new NodeDistance(v, uDist + weight));
                }
            }
        }
        return results;
    }

    private Vertex<T> findvertex(AdjacencyListGraph<T> graph, T data) {
        Iterator<Vertex<T>> iterator = graph.getVertices().iterator();
        while (iterator.hasNext()) {
            Vertex<T> vertex = iterator.next();
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }

    private int getdistance(LinkedList<PathResult> results, T data) {
        Iterator<PathResult> it = results.iterator();
        while (it.hasNext()) {
            PathResult pr = it.next();
            if (pr.data.equals(data)) {
                return pr.distance;
            }
        }
        return 999999;
    }

    private void updatedistance(LinkedList<PathResult> results, T data, int newDist) {
        Iterator<PathResult> it = results.iterator();
        while (it.hasNext()) {
            PathResult pr = it.next();
            if (pr.data.equals(data)) {
                pr.distance = newDist;
                return;
            }
        }
    }

    private boolean contains(LinkedList<T> list, T data) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(data)) {
                return true;
            }
        }
        return false;
    }
}