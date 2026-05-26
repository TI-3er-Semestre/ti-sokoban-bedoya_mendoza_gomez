import org.junit.jupiter.api.Test;

import structures.Graph.AdjacencyListGraph;
import structures.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class AdjacencyListGraphTest {

    private AdjacencyListGraph<String> setupGraph() {

        AdjacencyListGraph<String> graph =
                new AdjacencyListGraph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 1);
        graph.addEdge("B", "D", 1);

        return graph;
    }

    @Test
    void testAddVertex_standard() {
        // resultado esperado: el vertice existe

        AdjacencyListGraph<String> graph =
                new AdjacencyListGraph<>();

        graph.addVertex("A");

        assertTrue(graph.containsVertex("A"));
    }

    @Test
    void testContainsVertex_notFound() {

        // resultado esperado: flase

        AdjacencyListGraph<String> graph =
                new AdjacencyListGraph<>();

        assertFalse(graph.containsVertex("X"));
    }

    @Test
    void testAddEdge_standard() {
        //resultado esperado: la arista existe

        AdjacencyListGraph<String> graph =
                setupGraph();

        assertTrue(
                graph.containsEdge("A", "B")
        );
    }

    @Test
    void testContainsEdge_notFound() {
        // resultado esperado: false

        AdjacencyListGraph<String> graph =
                setupGraph();

        assertFalse(
                graph.containsEdge("B", "C")
        );
    }

    @Test
    void testBFS_standard() {

        // Escenario:
        // Grafo con recorrido BFS
        //
        // Entrada:
        // A conectado con B y C
        // B conectado con D
        //
        // Resultado esperado:
        // El recorrido debe visitar:
        // A, B, C y D

        AdjacencyListGraph<String> graph = setupGraph();

        LinkedList<String> result = graph.bfs("A");

        assertTrue(result.containsVisited("A"));
        assertTrue(result.containsVisited("B"));
        assertTrue(result.containsVisited("C"));
        assertTrue(result.containsVisited("D"));
    }

    @Test
    void testDFS_standard() {

        // Escenario:
        // Grafo con recorrido DFS
        //
        // Entrada:
        // A conectado con B y C
        // B conectado con D
        //
        // Resultado esperado:
        // El recorrido debe visitar
        // todos los nodos del grafo

        AdjacencyListGraph<String> graph = setupGraph();

        LinkedList<String> result = graph.dfs("A");

        assertTrue(result.containsVisited("A"));
        assertTrue(result.containsVisited("B"));
        assertTrue(result.containsVisited("C"));
        assertTrue(result.containsVisited("D"));
    }
}