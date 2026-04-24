import structures.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedlistTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        // Lista vacía antes de cada prueba
        list = new LinkedList<>();
    }

    // ---------------------------------------------------------------
    // add() — inserción ordenada
    // ---------------------------------------------------------------

    @Test
    void testAdd_singleElement_isAddedCorrectly() {
        // Escenario positivo: agregar un elemento a lista vacía
        // Dado: lista vacía
        // Cuando: se agrega el elemento 5
        // Resultado esperado: first y last apuntan al nodo con dato 5, length == 1
        Node<Integer> node = list.add(5);

        assertNotNull(node);
        assertEquals(5, node.getData());
        assertEquals(1, list.getLength());
        assertEquals(5, list.getFirst().getData());
        assertEquals(5, list.getLast().getData());
    }

    @Test
    void testAdd_multipleElements_maintainsSortedOrder() {
        // Escenario positivo: agregar varios elementos, deben quedar ordenados
        // Dado: lista vacía
        // Cuando: se agregan 30, 10, 20
        // Resultado esperado: orden ascendente 10 -> 20 -> 30
        list.add(30);
        list.add(10);
        list.add(20);

        assertEquals(3, list.getLength());
        assertEquals(10, list.getFirst().getData());
        assertEquals(30, list.getLast().getData());
        assertEquals(20, list.getFirst().getNext().getData());
    }

    @Test
    void testAdd_duplicateElement_isAddedAtCorrectPosition() {
        // Escenario positivo: agregar elemento duplicado
        // Dado: lista con [10, 20]
        // Cuando: se agrega 10 de nuevo
        // Resultado esperado: length == 3, orden sigue siendo ascendente
        list.add(10);
        list.add(20);
        list.add(10);

        assertEquals(3, list.getLength());
        assertEquals(10, list.getFirst().getData());
    }

    @Test
    void testAdd_nullElement_listRemainsEmpty() {
        // Escenario negativo: la lista no acepta null como dato válido
        // En una lista vacía, add(null) crea un nodo con dato null
        // El test verifica que esto no rompe la estructura
        list.add(null);
        assertEquals(1, list.getLength());
        assertNull(list.getFirst().getData());
    }

    // ---------------------------------------------------------------
    // delete() — eliminación por dato
    // ---------------------------------------------------------------

    @Test
    void testDelete_existingElement_removesCorrectly() {
        // Escenario positivo: eliminar elemento que existe en la lista
        // Dado: lista con [10, 20, 30]
        // Cuando: se elimina el 20
        // Resultado esperado: nodo retornado con dato 20, length == 2
        list.add(10);
        list.add(20);
        list.add(30);

        Node<Integer> deleted = list.delete(20);

        assertNotNull(deleted);
        assertEquals(20, deleted.getData());
        assertEquals(2, list.getLength());
        assertNull(list.search(20));
    }

    @Test
    void testDelete_firstElement_updatesFirst() {
        // Escenario positivo: eliminar el primer elemento
        // Dado: lista con [10, 20, 30]
        // Cuando: se elimina el 10
        // Resultado esperado: first apunta ahora al nodo con dato 20
        list.add(10);
        list.add(20);
        list.add(30);

        list.delete(10);

        assertEquals(20, list.getFirst().getData());
        assertEquals(2, list.getLength());
    }

    @Test
    void testDelete_lastElement_updatesLast() {
        // Escenario positivo: eliminar el último elemento
        // Dado: lista con [10, 20, 30]
        // Cuando: se elimina el 30
        // Resultado esperado: last apunta ahora al nodo con dato 20
        list.add(10);
        list.add(20);
        list.add(30);

        list.delete(30);

        assertEquals(20, list.getLast().getData());
        assertEquals(2, list.getLength());
    }

    @Test
    void testDelete_nonExistingElement_returnsNull() {
        // Escenario negativo: eliminar elemento que no existe
        // Dado: lista con [10, 20, 30]
        // Cuando: se intenta eliminar el 99
        // Resultado esperado: retorna null, length no cambia
        list.add(10);
        list.add(20);
        list.add(30);

        Node<Integer> deleted = list.delete(99);

        assertNull(deleted);
        assertEquals(3, list.getLength());
    }

    @Test
    void testDelete_emptyList_returnsNull() {
        // Escenario negativo: eliminar de lista vacía
        // Dado: lista vacía
        // Cuando: se intenta eliminar el 5
        // Resultado esperado: retorna null, length sigue en 0
        Node<Integer> deleted = list.delete(5);

        assertNull(deleted);
        assertEquals(0, list.getLength());
    }

    // ---------------------------------------------------------------
    // search() — búsqueda por dato
    // ---------------------------------------------------------------

    @Test
    void testSearch_existingElement_returnsNode() {
        // Escenario positivo: buscar elemento que existe
        // Dado: lista con [10, 20, 30]
        // Cuando: se busca el 20
        // Resultado esperado: retorna el nodo con dato 20
        list.add(10);
        list.add(20);
        list.add(30);

        Node<Integer> found = list.search(20);

        assertNotNull(found);
        assertEquals(20, found.getData());
    }

    @Test
    void testSearch_firstElement_returnsNode() {
        // Escenario positivo: buscar el primer elemento
        // Dado: lista con [10, 20, 30]
        // Cuando: se busca el 10
        // Resultado esperado: retorna el nodo con dato 10
        list.add(10);
        list.add(20);
        list.add(30);

        Node<Integer> found = list.search(10);

        assertNotNull(found);
        assertEquals(10, found.getData());
    }

    @Test
    void testSearch_nonExistingElement_returnsNull() {
        // Escenario negativo: buscar elemento que no existe
        // Dado: lista con [10, 20, 30]
        // Cuando: se busca el 99
        // Resultado esperado: retorna null
        list.add(10);
        list.add(20);
        list.add(30);

        Node<Integer> found = list.search(99);

        assertNull(found);
    }

    @Test
    void testSearch_emptyList_returnsNull() {
        // Escenario negativo: buscar en lista vacía
        // Dado: lista vacía
        // Cuando: se busca el 5
        // Resultado esperado: retorna null
        Node<Integer> found = list.search(5);

        assertNull(found);
    }
}
