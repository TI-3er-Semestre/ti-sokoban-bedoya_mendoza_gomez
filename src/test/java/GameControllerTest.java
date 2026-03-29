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
    // escenarios de prueba
    // ---------------------------------------------------------------
    /**
     * setupStage1:
     * Tablero 5×5
     * Jugador en (2,2)
     * Celda libre en (2,1)
     * Muro en (2,0)
     */
    private GameState setupStage1() {
        Board board = new Board(5, 5);

        board.setCell(2, 2, new PlayerCell());
        // la celda libre
        board.setCell(2, 1, new EmptyCell());
        // el muro
        board.setCell(2, 0, new WallCell());

        return new GameState(board, new Position(2, 2), 1);
    }

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


    /**
     * PRUEBA 1
      */

    @Test
    void testMoveLeft_validMove() {
        // Arrange
        controller.setStateForTesting(setupStage1());
        // Act
        boolean result = controller.move(Direction.LEFT);

        // Assert
        assertTrue(result, "El jugador debería poder moverse a la izquierda");
        assertEquals(2, controller.getCurrentState().getPlayerPosition().getRow());
        assertEquals(1, controller.getCurrentState().getPlayerPosition().getCol());
    }

    @Test
    void testMoveUp_validMove() {
        // Arrange
        controller.setStateForTesting(setupStage1());

        // Act
        boolean result = controller.move(Direction.UP);

        // Assert
        assertTrue(result);
        assertEquals(1, controller.getCurrentState().getPlayerPosition().getRow());
        assertEquals(2, controller.getCurrentState().getPlayerPosition().getCol());
    }

    @Test
    void testMoveDown_validMove() {
        // Arrange
        controller.setStateForTesting(setupStage1());

        // Act
        boolean result = controller.move(Direction.DOWN);

        // Assert
        assertTrue(result);
        assertEquals(3, controller.getCurrentState().getPlayerPosition().getRow());
        assertEquals(2, controller.getCurrentState().getPlayerPosition().getCol());
    }

    @Test
    void testMoveRight_validMove() {
        // Arrange
        controller.setStateForTesting(setupStage1());

        // Act
        boolean result = controller.move(Direction.RIGHT);
        // Assert
        assertTrue(result);
        assertEquals(2, controller.getCurrentState().getPlayerPosition().getRow());
        assertEquals(3, controller.getCurrentState().getPlayerPosition().getCol());
    }

    @Test
    void testMoveLeftTwice_hitsWall() {
        // Arrange
        controller.setStateForTesting(setupStage1());

        // Act
        controller.move(Direction.LEFT);
        boolean result = controller.move(Direction.LEFT);

        // Assert
        assertFalse(result, "Debe bloquearse por muro");
        assertEquals(2, controller.getCurrentState().getPlayerPosition().getRow());
        assertEquals(1, controller.getCurrentState().getPlayerPosition().getCol());
    }


    /**
     * PRUEBA 2
     */

    @Test
    void testIsValidMove_left_isTrue() {
        // Arrange
        controller.setStateForTesting(setupStage1());

        // Act
        boolean result = controller.move(Direction.LEFT);

        // Assert
        assertTrue(result, "El movimiento deberia ser valido (celda libre)");
    }


    @Test
    void testIsValidMove_left_inWall_isFalse() {
        // Arrange
        controller.setStateForTesting(setupStage1());

        // Act
        controller.move(Direction.LEFT); // primer movimiento valido
        boolean result = controller.move(Direction.LEFT); // segundo movimiento muro

        // Assert
        assertFalse(result, "El movimiento deberia ser invalido por que hay un muro");
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
