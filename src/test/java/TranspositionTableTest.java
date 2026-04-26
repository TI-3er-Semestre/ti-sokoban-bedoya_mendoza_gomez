import org.junit.jupiter.api.Test;
import structures.TranspositionTable;
import structures.StateKey;
import Model.Position;

import static org.junit.jupiter.api.Assertions.*;

public class TranspositionTableTest {

    private StateKey createKey(int r, int c) {
        return new StateKey(new Position(r, c), new Position[]{});
    }

    @Test
    void testAdd_andContains() {
        // Escenario: Tabla vacía
        // Entrada: agregar un StateKey
        // Resultado esperado: contains() retorna true

        TranspositionTable table = new TranspositionTable(10);

        StateKey key = createKey(1,1);
        table.add(key);

        assertTrue(table.contains(key));
    }

    @Test
    void testContains_notExisting() {
        // Escenario: Tabla sin el elemento
        // Entrada: buscar key no insertado
        // Resultado esperado: retorna false

        TranspositionTable table = new TranspositionTable(10);

        assertFalse(table.contains(createKey(2,2)));
    }

    @Test
    void testAdd_duplicate() {
        // Escenario: Inserción duplicada
        // Entrada: agregar el mismo key dos veces
        // Resultado esperado: no se duplica

        TranspositionTable table = new TranspositionTable(10);

        StateKey key = createKey(1,1);
        table.add(key);
        table.add(key);

        assertTrue(table.contains(key));
    }
}