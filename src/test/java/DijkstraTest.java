import org.junit.jupiter.api.Test;
import structures.Graph.AdjacencyListGraph;
import structures.Graph.Dijkstra;
import structures.LinkedList;
import structures.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {

    @Test
    void testShortestPaths_findsCorrectDistances() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");

        graph.addEdge("a", "b", 2);
        graph.addEdge("b", "c", 3);
        graph.addEdge("a", "c", 10);

        Dijkstra<String> dijkstra = new Dijkstra<>();
        LinkedList<Dijkstra<String>.PathResult> results = dijkstra.shortestpaths(graph, "a");

        int distToC = -1;
        Iterator<Dijkstra<String>.PathResult> it = results.iterator();
        while (it.hasNext()) {
            Dijkstra<String>.PathResult pr = it.next();
            if (pr.data.equals("c")) distToC = pr.distance;
        }

        // la ruta mas corta a 'c' es a,b,c (2+3 = 5) no la directa (10)
        assertEquals(5, distToC);
    }
}