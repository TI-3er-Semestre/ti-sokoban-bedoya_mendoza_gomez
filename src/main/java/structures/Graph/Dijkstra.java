package structures.Graph;

import structures.PriorityQueue;
import structures.LinkedList;
import structures.Iterator;

public class Dijkstra<T extends Comparable<T>> {

    // Clase auxiliar para guardar en la cola de prioridad
    public class NodeDistance implements Comparable<NodeDistance> {
        private T vertexData;
        private int distance;

        public NodeDistance(T vertexData, int distance) {
            this.vertexData = vertexData;
            this.distance = distance;
        }

        public T getVertexData() { return vertexData; }
        public int getDistance() { return distance; }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance); // Ordena de menor a mayor distancia
        }
    }

    // Clase auxiliar para la tabla de distancias usando tu LinkedList
    public class DistanceEntry implements Comparable<DistanceEntry> {
        private T vertexData;
        public int distance; // public para poder actualizarla fácilmente

        public DistanceEntry(T vertexData, int distance) {
            this.vertexData = vertexData;
            this.distance = distance;
        }

        @Override
        public int compareTo(DistanceEntry other) {
            return this.vertexData.compareTo(other.vertexData);
        }
    }

    public void findShortestPath(AdjacencyListGraph<T> graph, T start) {
        // Usamos tu cola de prioridad con capacidad suficiente
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(1000);
        LinkedList<DistanceEntry> distances = new LinkedList<>();
        LinkedList<T> visited = new LinkedList<>();

        // Inicializar distancias a infinito (Integer.MAX_VALUE)
        Iterator<Vertex<T>> it = graph.getVertices().iterator();
        while (it.hasNext()) {
            T data = it.next().getData();
            if (data.equals(start)) {
                distances.add(new DistanceEntry(data, 0));
            } else {
                distances.add(new DistanceEntry(data, Integer.MAX_VALUE));
            }
        }

        queue.enqueue(new NodeDistance(start, 0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.dequeue();
            T currentData = current.getVertexData();

            // --------------------------------------------------------------------
            // LÍNEAS CLAVE PARA EL VIDEO:
            // Aquí es donde Dijkstra falla con pesos negativos (baldosas de energía -2).
            // Al marcar el nodo como visitado y saltarlo si ya estaba en la lista,
            // asume de forma "avara" (greedy) que ya encontró la ruta definitiva,
            // ignorando si más adelante había un camino más barato.
            if (contains(visited, currentData)) {
                continue;
            }
            visited.add(currentData);
            // --------------------------------------------------------------------

            Vertex<T> currentVertex = findVertex(graph, currentData);
            if (currentVertex != null) {
                Iterator<Edge<T>> edgeIt = currentVertex.getEdges().iterator();
                while (edgeIt.hasNext()) {
                    Edge<T> edge = edgeIt.next();
                    T neighborData = edge.getDestination().getData();
                    int weight = edge.getWeight();

                    DistanceEntry currentDistEntry = findDistanceEntry(distances, currentData);
                    DistanceEntry neighborDistEntry = findDistanceEntry(distances, neighborData);

                    if (currentDistEntry != null && neighborDistEntry != null) {
                        int currentDist = currentDistEntry.distance;
                        int neighborDist = neighborDistEntry.distance;

                        // Relajación de aristas
                        if (currentDist != Integer.MAX_VALUE && currentDist + weight < neighborDist) {
                            neighborDistEntry.distance = currentDist + weight;
                            queue.enqueue(new NodeDistance(neighborData, currentDist + weight));
                        }
                    }
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

    private DistanceEntry findDistanceEntry(LinkedList<DistanceEntry> list, T data) {
        Iterator<DistanceEntry> iterator = list.iterator();
        while (iterator.hasNext()) {
            DistanceEntry entry = iterator.next();
            if (entry.vertexData.equals(data)) {
                return entry;
            }
        }
        return null;
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