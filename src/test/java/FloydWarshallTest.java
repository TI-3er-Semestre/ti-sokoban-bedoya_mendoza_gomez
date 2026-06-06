import org.junit.jupiter.api.Test;
import Model.structures.Graph.AdjacencyMatrixGraph;
import Model.structures.Graph.FloydWarshall;

import static org.junit.jupiter.api.Assertions.*;

public class FloydWarshallTest {

    @Test
    void testCalculate_findsAllShortestPaths() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");

        graph.addEdge("a", "b", 2);
        graph.addEdge("b", "c", 3);
        graph.addEdge("a", "c", 10);

        FloydWarshall fw = new FloydWarshall();
        int[][] shortestPaths = fw.calculate(graph.getmatrix(), graph.getsize());

        // el camino mas corto de 'a' con indice 0 a 'c' con indice 2 debe ser 5
        assertEquals(5, shortestPaths[0][2]);
    }
}