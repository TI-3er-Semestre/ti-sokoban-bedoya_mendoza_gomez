import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba 5: Verificación de victoria — Board.
 * Verifica que isCompleted() detecte correctamente
 * cuando todas las cajas están sobre los objetivos.
 */
public class BoardTest {

    // ---------------------------------------------------------------
    // isCompleted — condición de victoria
    // ---------------------------------------------------------------

    @Test
    void testIsCompleted_allBoxesOnGoals_returnsTrue() {
        // Escenario: setupStage6  — tablero 4x4, ambas cajas sobre objetivos
        // Entrada: ninguna (se evalúa el estado del tablero)
        // Resultado esperado: true — todas las goalPositions cubiertas por BoxOnGoalCell
        Board b = new Board(4, 4);
        b.setCell(0, 0, new BoxOnGoalCell());
        b.setCell(0, 1, new BoxOnGoalCell());
        b.setCell(3, 3, new PlayerCell());
        b.setGoalPositions(new Position[]{
                new Position(0, 0),
                new Position(0, 1)
        });
        assertTrue(b.isCompleted());
    }

    @Test
    void testIsCompleted_oneBoxNotOnGoal_returnsFalse() {
        // Escenario: setupStage5 — una goalPosition sin caja
        // Entrada: ninguna
        // Resultado esperado: false — al menos una GoalCell sin caja encima
        Board b = new Board(4, 4);
        b.setCell(0, 0, new BoxOnGoalCell());
        b.setCell(0, 1, new GoalCell());
        b.setCell(1, 1, new BoxCell());
        b.setCell(3, 3, new PlayerCell());
        b.setGoalPositions(new Position[]{
                new Position(0, 0),
                new Position(0, 1)
        });
        assertFalse(b.isCompleted());
    }

    @Test
    void testIsCompleted_noBoxesAtAll_returnsFalse() {
        // Escenario: objetivos sin ninguna caja encima
        // Resultado esperado: false
        Board b = new Board(4, 4);
        b.setCell(0, 0, new GoalCell());
        b.setGoalPositions(new Position[]{ new Position(0, 0) });
        assertFalse(b.isCompleted());
    }
}