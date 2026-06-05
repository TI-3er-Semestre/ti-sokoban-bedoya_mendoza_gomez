package Model;

import structures.Queue;
import structures.Stack;
import structures.TranspositionTable;
import structures.StateKey;

/**
 * solucionador para los niveles de sokoban
 * usa recorrido de grafos implicito para encontrar la secuencia de movimientos ganadora
 */
public class SokobanSolver {

    /**
     * nodo interno para rastrear el camino y el estado en el arbol de busqueda
     */
    private static class SearchNode {
        GameState state;
        SearchNode parent;
        String move;

        public SearchNode(GameState state, SearchNode parent, String move) {
            this.state = state;
            this.parent = parent;
            this.move = move;
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

        SearchNode startNode = new SearchNode(initial.clone(), null, null);
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
                        frontier.enqueue(new SearchNode(nextState, current, dirtostring(dir)));
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

        SearchNode startNode = new SearchNode(initial.clone(), null, null);
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
                        frontier.push(new SearchNode(nextState, current, dirtostring(dir)));
                    }
                }
            }
        }
        return new Queue<>(); // retorna vacio si no encuentra solucion
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