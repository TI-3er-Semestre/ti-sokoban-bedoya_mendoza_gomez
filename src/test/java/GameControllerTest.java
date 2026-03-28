import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba 5: Verificación de victoria — GameController.
 * Verifica que checkVictory() detecte correctamente el estado de victoria.
 * Runs independently of the graphical interface.
 */
public class GameControllerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        // Sin rutas de archivo — las pruebas inyectan el estado directamente
        controller = new GameController(null, null, null);
    }

    // ---------------------------------------------------------------
    // Helpers — escenarios de prueba
    // ---------------------------------------------------------------

    /**
     * setupStage6: tablero 4x4.
     * Ambas cajas sobre sus objetivos. Jugador en (3,3). Estado ganador.
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
     * Una caja sobre objetivo (0,0). Una caja fuera en (1,1).
     * Objetivo sin cubrir en (0,1). Jugador en (3,3). Estado no ganador.
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
    // checkVictory()
    // ---------------------------------------------------------------

    @Test
    void testCheckVictory_allBoxesOnGoals_returnsTrue() {
        // Escenario: setupStage6 — estado ganador
        // Entrada: ninguna
        // Resultado esperado: true — el controlador detecta la victoria
        controller.setStateForTesting(setupStage6());
        assertTrue(controller.checkVictory());
    }

    @Test
    void testCheckVictory_notAllBoxesOnGoals_returnsFalse() {
        // Escenario: setupStage5 — estado no ganador
        // Entrada: ninguna
        // Resultado esperado: false — no todas las cajas en objetivos
        controller.setStateForTesting(setupStage5());
        assertFalse(controller.checkVictory());
    }
}
