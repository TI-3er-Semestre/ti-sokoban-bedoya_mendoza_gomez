import org.junit.jupiter.api.Test;
import structures.Graph.AdjacencyMatrixGraph;
import structures.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class AdjacencyMatrixGraphTest {

    private AdjacencyMatrixGraph<String> setupGraph() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");

        graph.addEdge("a", "b", 1);
        graph.addEdge("a", "c", 1);
        graph.addEdge("b", "d", 1);

        return graph;
    }

    @Test
    void testAddVertex_standard() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("a");
        assertTrue(graph.containsVertex("a"));
    }

    @Test
    void testContainsVertex_notFound() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        assertFalse(graph.containsVertex("x"));
    }

    @Test
    void testAddEdge_standard() {
        AdjacencyMatrixGraph<String> graph = setupGraph();
        assertTrue(graph.containsEdge("a", "b"));
    }

    @Test
    void testContainsEdge_notFound() {
        AdjacencyMatrixGraph<String> graph = setupGraph();
        assertFalse(graph.containsEdge("b", "c"));
    }

    @Test
    void testBfs_standard() {
        AdjacencyMatrixGraph<String> graph = setupGraph();
        LinkedList<String> result = graph.bfs("a");

        assertNotNull(result);
        assertTrue(result.containsVisited("a"));
        assertTrue(result.containsVisited("b"));
        assertTrue(result.containsVisited("c"));
        assertTrue(result.containsVisited("d"));
    }

    @Test
    void testDfs_standard() {
        AdjacencyMatrixGraph<String> graph = setupGraph();
        LinkedList<String> result = graph.dfs("a");

        assertNotNull(result);
        assertTrue(result.containsVisited("a"));
        assertTrue(result.containsVisited("b"));
        assertTrue(result.containsVisited("c"));
        assertTrue(result.containsVisited("d"));
    }
}