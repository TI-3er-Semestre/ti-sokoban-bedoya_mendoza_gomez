import Model.*;
import org.junit.jupiter.api.Test;
import Model.structures.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class SokobanSolverTest {

    private GameState setuptestlevel() {
        Board board = new Board(5, 5);
        //tablero simple que se puede ganar
        //jugador en (1,1), caja en (1,2), objetivo en (1,3)
        board.setCell(1, 1, new PlayerCell());
        board.setCell(1, 2, new BoxCell());
        board.setCell(1, 3, new GoalCell());

        board.setGoalPositions(new Position[]{ new Position(1, 3) });
        return new GameState(board, new Position(1, 1), 1);
    }

    @Test
    void testSolveBfs_findsSolution() {

        SokobanSolver solver = new SokobanSolver(1000);
        Queue<String> solution = solver.solvebfs(setuptestlevel());

        assertNotNull(solution);
        assertFalse(solution.isEmpty(), "la cola de solucion no debe estar vacia");
        assertEquals("d", solution.dequeue(), "el movimiento ganador debe ser empujar a la derecha (d)");
    }

    @Test
    void testSolveDfs_findsSolution() {
        SokobanSolver solver = new SokobanSolver(1000);
        Queue<String> solution = solver.solvedfs(setuptestlevel());

        assertNotNull(solution);
        assertFalse(solution.isEmpty(), "la cola de solucion no debe estar vacia");
    }
}