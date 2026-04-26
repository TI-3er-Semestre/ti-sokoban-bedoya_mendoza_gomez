import org.junit.jupiter.api.Test;
import structures.LinkedList;
import structures.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorTest {

    // ---------------------------------------------------------------
    // SETUPS
    // ---------------------------------------------------------------

    private LinkedList<Integer> setupStage1() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }

    private LinkedList<Integer> setupStage2() {
        return new LinkedList<>(); // lista vacía
    }

    private LinkedList<Integer> setupStage3() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(5);
        return list;
    }

    // ---------------------------------------------------------------
// hasNext()
// ---------------------------------------------------------------

    @Test
    void testHasNext_standard() {
        // Escenario: lista con múltiples elementos (1,2,3)
        // Entrada: iterador sobre la lista
        // Resultado esperado: true — existen elementos por recorrer
        LinkedList<Integer> list = setupStage1();
        Iterator<Integer> it = list.iterator();

        assertTrue(it.hasNext());
    }

    @Test
    void testHasNext_emptyList() {
        // Escenario: lista vacía
        // Entrada: iterador sobre la lista
        // Resultado esperado: false — no hay elementos
        LinkedList<Integer> list = setupStage2();
        Iterator<Integer> it = list.iterator();

        assertFalse(it.hasNext());
    }

    @Test
    void testHasNext_singleElement() {
        // Escenario: lista con un solo elemento
        // Entrada: iterador sobre la lista
        // Resultado esperado:
        //   - true antes de consumir el elemento
        //   - false después de consumirlo
        LinkedList<Integer> list = setupStage3();
        Iterator<Integer> it = list.iterator();

        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }

// ---------------------------------------------------------------
// next()
// ---------------------------------------------------------------

    @Test
    void testNext_standardIteration() {
        // Escenario: lista con elementos ordenados (1,2,3)
        // Entrada: iterador recorriendo la lista
        // Resultado esperado: retorna los elementos en orden 1 → 2 → 3
        LinkedList<Integer> list = setupStage1();
        Iterator<Integer> it = list.iterator();

        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());
    }

    @Test
    void testNext_emptyList_returnsNullOrFailsGracefully() {
        // Escenario: lista vacía
        // Entrada: iterador sobre lista vacía
        // Resultado esperado: no hay elementos (hasNext() == false)
        //                   no se debe iterar
        LinkedList<Integer> list = setupStage2();
        Iterator<Integer> it = list.iterator();

        assertFalse(it.hasNext());
    }

    @Test
    void testNext_singleElement() {
        // Escenario: lista con un solo elemento (5)
        // Entrada: iterador
        // Resultado esperado: retorna 5 en la primera llamada
        LinkedList<Integer> list = setupStage3();
        Iterator<Integer> it = list.iterator();

        assertEquals(5, it.next());
    }
}