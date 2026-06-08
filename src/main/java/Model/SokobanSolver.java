package Model;

import Model.structures.Queue;
import Model.structures.Stack;
import Model.structures.TranspositionTable;
import Model.structures.StateKey;
import controller.GameController;
import Model.structures.PriorityQueue;
import Model.structures.Graph.FloydWarshall;

/**
 * solucionador para los niveles de sokoban
 * usa recorrido de grafos implicito para encontrar la secuencia de movimientos ganadora
 */
public class SokobanSolver {

    /**
     * nodo interno para rastrear el camino, el estado y el costo
     */
    private static class SearchNode implements Comparable<SearchNode> {
        GameState state;
        SearchNode parent;
        String move;
        int cost;

        public SearchNode(GameState state, SearchNode parent, String move, int cost) {
            this.state = state;
            this.parent = parent;
            this.move = move;
            this.cost = cost;
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    private int maxStates;

    public SokobanSolver(int maxStates) {
        this.maxStates = maxStates;
    }

    /**
     * resuelve el nivel usando bfs
     * garantiza el camino mas corto de movimientos
     * @param initial el estado inicial del juego
     * @return una cola de strings con los movimientos ganadores
     */
    public Queue<String> solvebfs(GameState initial) {
        if (initial == null) return new Queue<>();

        Queue<SearchNode> frontier = new Queue<>();
        TranspositionTable visited = new TranspositionTable(maxStates);

        SearchNode startNode = new SearchNode(initial.clone(), null, null, 0);
        frontier.enqueue(startNode);

        StateKey startKey = new StateKey(initial.getPlayerPosition(), initial.getBoard().getBoxPositions());
        visited.add(startKey);

        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.dequeue();

            if (current.state.isVictory()) {
                return buildPath(current);
            }

            for (int i = 0; i < 4; i++) {
                Direction dir = directions[i];


                GameController simulator = new GameController(null, null, null);
                simulator.setStateForTesting(current.state.clone());

                if (simulator.move(dir)) {
                    GameState nextState = simulator.getCurrentState();
                    StateKey nextKey = new StateKey(nextState.getPlayerPosition(), nextState.getBoard().getBoxPositions());

                    if (!visited.contains(nextKey)) {
                        visited.add(nextKey);
                        frontier.enqueue(new SearchNode(nextState, current, dirtostring(dir),0));
                    }
                }
            }
        }
        return new Queue<>(); //retorna vacio si no encuentra solucion
    }

    /**
     * resuelve el nivel usandodfs
     * encuentra un camino a ganar aunque no sea el mas corto
     * @param initial el estado inicial del juego
     * @return una cola de strings con los movimientos ganadores
     */
    public Queue<String> solvedfs(GameState initial) {
        if (initial == null) return new Queue<>();

        Stack<SearchNode> frontier = new Stack<>();
        TranspositionTable visited = new TranspositionTable(maxStates);

        SearchNode startNode = new SearchNode(initial.clone(), null, null,0);
        frontier.push(startNode);

        StateKey startKey = new StateKey(initial.getPlayerPosition(), initial.getBoard().getBoxPositions());
        visited.add(startKey);

        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.pop();

            if (current.state.isVictory()) {
                return buildPath(current);
            }

            for (int i = 0; i < 4; i++) {
                Direction dir = directions[i];

                GameController simulator = new GameController(null, null, null);
                simulator.setStateForTesting(current.state.clone());

                if (simulator.move(dir)) {
                    GameState nextState = simulator.getCurrentState();
                    StateKey nextKey = new StateKey(nextState.getPlayerPosition(), nextState.getBoard().getBoxPositions());

                    if (!visited.contains(nextKey)) {
                        visited.add(nextKey);
                        frontier.push(new SearchNode(nextState, current, dirtostring(dir),0));
                    }
                }
            }
        }
        return new Queue<>(); // retorna vacio si no encuentra solucion
    }

    /**
     * Resuelve el nivel usando Dijkstra (Costo Uniforme) sobre el grafo implícito.
     * Utiliza la PriorityQueue personalizada de las estructuras discretas.
     */
    public Queue<String> solvedijkstra(GameState initial) {
        if (initial == null) return new Queue<>();

        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(maxStates);
        TranspositionTable visited = new TranspositionTable(maxStates);

        SearchNode startNode = new SearchNode(initial.clone(), null, null, 0);
        frontier.enqueue(startNode);

        StateKey startKey = new StateKey(initial.getPlayerPosition(), initial.getBoard().getBoxPositions());
        visited.add(startKey);

        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.dequeue(); //saca siempre el camino mas barato/corto

            if (current.state.isVictory()) {
                return buildPath(current);
            }

            for (int i = 0; i < 4; i++) {
                Direction dir = directions[i];

                GameController simulator = new GameController(null, null, null);
                simulator.setStateForTesting(current.state.clone());

                if (simulator.move(dir)) {
                    GameState nextState = simulator.getCurrentState();
                    StateKey nextKey = new StateKey(nextState.getPlayerPosition(), nextState.getBoard().getBoxPositions());

                    if (!visited.contains(nextKey)) {
                        visited.add(nextKey);

                        frontier.enqueue(new SearchNode(nextState, current, dirtostring(dir), current.cost + 1));
                    }
                }
            }
        }
        return new Queue<>();
    }

    /**
     * Implementación demostrativa de Floyd-Warshall.
     * Aplica FW al mapa físico para probar la clase explícita y luego retorna la ruta óptima.
     */
    public Queue<String> solvefloyd(GameState initial) {
        if (initial == null) return new Queue<>();


        //dimensiones del mapa
        int rows = initial.getBoard().getRows();
        int cols = initial.getBoard().getCols();
        int size = rows * cols;
        int[][] adjMatrix = new int[size][size];

        //mapeo de  celdas físicas adyacentes para armar el grafo explicito
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int u = r * cols + c;
                if (!initial.getBoard().getCell(r, c).isWalkable()) continue;

                //vecinos válidos
                if (r > 0 && initial.getBoard().getCell(r-1, c).isWalkable()) adjMatrix[u][(r-1)*cols + c] = 1;
                if (r < rows-1 && initial.getBoard().getCell(r+1, c).isWalkable()) adjMatrix[u][(r+1)*cols + c] = 1;
                if (c > 0 && initial.getBoard().getCell(r, c-1).isWalkable()) adjMatrix[u][r*cols + (c-1)] = 1;
                if (c < cols-1 && initial.getBoard().getCell(r, c+1).isWalkable()) adjMatrix[u][r*cols + (c+1)] = 1;
            }
        }

        FloydWarshall fw = new FloydWarshall();
        int[][] shortestPhysicalPaths = fw.calculate(adjMatrix, size);
        


        return solvedijkstra(initial);
    }

    /**
     * retrocede desde el nodo de victoria para generar la secuencia de movimientos
     */
    private Queue<String> buildPath(SearchNode node) {
        Stack<String> reverse = new Stack<>();
        while (node.parent != null) {
            reverse.push(node.move);
            node = node.parent;
        }

        Queue<String> path = new Queue<>();
        while (!reverse.isEmpty()) {
            path.enqueue(reverse.pop());
        }

        return path;
    }

    /**
     * mapea una direccion a las teclas de entrada requeridas
     */
    private String dirtostring(Direction dir) {
        switch (dir) {
            case UP: return "w";
            case DOWN: return "s";
            case LEFT: return "a";
            case RIGHT: return "d";
            default: return "";
        }
    }
}