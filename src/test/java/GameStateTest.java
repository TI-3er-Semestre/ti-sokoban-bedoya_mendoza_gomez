import Model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    /**
     * setupStage6: tablero 4x4.
     * 2 objetivos en (0,0) y (0,1).
     * Ambas cajas sobre sus objetivos. Estado ganador.
     */
    private GameState setupStage6() {
        Board board = new Board(4, 4);
        board.setCell(0, 0, new BoxOnGoalCell());
        board.setCell(0, 1, new BoxOnGoalCell());
        board.setCell(3, 3, new PlayerCell());
        board.setGoalPositions(new Position[]{
                new Position(0, 0),
                new Position(0, 1)
        });
        return new GameState(board, new Position(3, 3), 1);
    }

    /**
     * setupStage5: tablero 4x4.
     * 2 objetivos en (0,0) y (0,1).
     * Una caja sobre objetivo en (0,0), otra fuera en (1,1).
     * Estado no ganador.
     */
    private GameState setupStage5() {
        Board board = new Board(4, 4);
        board.setCell(0, 0, new BoxOnGoalCell());
        board.setCell(0, 1, new GoalCell());
        board.setCell(1, 1, new BoxCell());
        board.setCell(3, 3, new PlayerCell());
        board.setGoalPositions(new Position[]{
                new Position(0, 0),
                new Position(0, 1)
        });
        return new GameState(board, new Position(3, 3), 1);
    }

    // ---------------------------------------------------------------
    // isVictory()
    // ---------------------------------------------------------------

    @Test
    void testIsVictory_allBoxesOnGoals() {
        // Escenario: setupStage6 — estado ya completo
        // Entrada: ninguna (se evalúa el estado actual)
        // Resultado esperado: true — todas las cajas sobre objetivos
        GameState state = setupStage6();
        assertTrue(state.isVictory());
    }

    @Test
    void testIsVictory_oneBoxOffGoal() {
        // Escenario: setupStage5 — solo una caja en objetivo
        // Entrada: ninguna
        // Resultado esperado: false — una caja fuera del objetivo, no hay victoria
        GameState state = setupStage5();
        assertFalse(state.isVictory());
    }
}
