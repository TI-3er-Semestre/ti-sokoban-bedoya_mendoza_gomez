import Model.structures.BinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for BinarySearchTree — written BEFORE implementation (TDD).
 * Covers: add(), delete(), search().
 * Each method has a positive and a negative scenario.
 * Runs independently of the graphical interface.
 */

public class BinarysearchtreeTest {

    private BinarySearchTree<Integer> bst;

    @BeforeEach
    void setUp() {
        // BST vacío antes de cada prueba
        bst = new BinarySearchTree<>();
    }

    // ---------------------------------------------------------------
    // add() — inserción ordenada
    // ---------------------------------------------------------------

    @Test
    void testAdd_singleElement_becomesRoot() {
        // Escenario positivo: agregar un elemento a BST vacío
        // Dado: BST vacío
        // Cuando: se agrega el elemento 10
        // Resultado esperado: la raíz tiene dato 10, tamaño == 1
        bst.add(10);

        assertNotNull(bst.getRoot());
        assertEquals(10, bst.getRoot().getData());
        assertEquals(1, bst.getSize());
    }

    @Test
    void testAdd_multipleElements_maintainsBSTProperty() {
        // Escenario positivo: agregar varios elementos respeta la propiedad BST
        // Dado: BST vacío
        // Cuando: se agregan 20, 10, 30
        // Resultado esperado: raíz == 20, hijo izquierdo == 10, hijo derecho == 30
        bst.add(20);
        bst.add(10);
        bst.add(30);

        assertEquals(20, bst.getRoot().getData());
        assertEquals(10, bst.getRoot().getLeft().getData());
        assertEquals(30, bst.getRoot().getRight().getData());
        assertEquals(3, bst.getSize());
    }

    @Test
    void testAdd_smallerElement_goesToLeftSubtree() {
        // Escenario positivo: elemento menor va al subárbol izquierdo
        // Dado: BST con raíz 20
        // Cuando: se agrega 5
        // Resultado esperado: 5 queda en el subárbol izquierdo de 20
        bst.add(20);
        bst.add(5);

        assertNotNull(bst.getRoot().getLeft());
        assertEquals(5, bst.getRoot().getLeft().getData());
    }

    @Test
    void testAdd_duplicateElement_isNotAdded() {
        // Escenario negativo: agregar elemento duplicado no modifica el BST
        // Dado: BST con elemento 10
        // Cuando: se intenta agregar 10 de nuevo
        // Resultado esperado: tamaño sigue siendo 1
        bst.add(10);
        bst.add(10);

        assertEquals(1, bst.getSize());
    }

    @Test
    void testAdd_nullElement_throwsException() {
        // Escenario negativo: agregar null lanza excepción
        // Dado: BST vacío
        // Cuando: se intenta agregar null
        // Resultado esperado: IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> bst.add(null));
    }

    // ---------------------------------------------------------------
    // delete() — eliminación por dato
    // ---------------------------------------------------------------

    @Test
    void testDelete_leafNode_removesCorrectly() {
        // Escenario positivo: eliminar nodo hoja (sin hijos)
        // Dado: BST con [20, 10, 30]
        // Cuando: se elimina 10 (nodo hoja)
        // Resultado esperado: 10 ya no existe, tamaño == 2
        bst.add(20);
        bst.add(10);
        bst.add(30);

        bst.delete(10);

        assertNull(bst.search(10));
        assertEquals(2, bst.getSize());
    }

    @Test
    void testDelete_nodeWithTwoChildren_maintainsBSTProperty() {
        // Escenario positivo: eliminar nodo con dos hijos
        // Dado: BST con [20, 10, 30, 25, 35]
        // Cuando: se elimina 30 (tiene hijos 25 y 35)
        // Resultado esperado: BST sigue siendo válido, 30 ya no existe
        bst.add(20);
        bst.add(10);
        bst.add(30);
        bst.add(25);
        bst.add(35);

        bst.delete(30);

        assertNull(bst.search(30));
        assertEquals(4, bst.getSize());
        // La propiedad BST se mantiene
        assertNotNull(bst.search(25));
        assertNotNull(bst.search(35));
    }

    @Test
    void testDelete_rootNode_updatesRoot() {
        // Escenario positivo: eliminar la raíz
        // Dado: BST con [20, 10, 30]
        // Cuando: se elimina 20 (la raíz)
        // Resultado esperado: nueva raíz válida, tamaño == 2
        bst.add(20);
        bst.add(10);
        bst.add(30);

        bst.delete(20);

        assertNull(bst.search(20));
        assertEquals(2, bst.getSize());
        assertNotNull(bst.getRoot());
    }

    @Test
    void testDelete_nonExistingElement_doesNotChangeSize() {
        // Escenario negativo: eliminar elemento que no existe
        // Dado: BST con [20, 10, 30]
        // Cuando: se intenta eliminar 99
        // Resultado esperado: tamaño sigue siendo 3
        bst.add(20);
        bst.add(10);
        bst.add(30);

        bst.delete(99);

        assertEquals(3, bst.getSize());
    }

    @Test
    void testDelete_emptyBST_doesNotThrow() {
        // Escenario negativo: eliminar de BST vacío no lanza excepción
        // Dado: BST vacío
        // Cuando: se intenta eliminar 10
        // Resultado esperado: no lanza excepción, tamaño sigue en 0
        assertDoesNotThrow(() -> bst.delete(10));
        assertEquals(0, bst.getSize());
    }

    // ---------------------------------------------------------------
    // search() — búsqueda por dato
    // ---------------------------------------------------------------

    @Test
    void testSearch_existingElement_returnsNode() {
        // Escenario positivo: buscar elemento que existe
        // Dado: BST con [20, 10, 30]
        // Cuando: se busca 10
        // Resultado esperado: retorna nodo con dato 10
        bst.add(20);
        bst.add(10);
        bst.add(30);

        assertNotNull(bst.search(10));
        assertEquals(10, bst.search(10).getData());
    }

    @Test
    void testSearch_rootElement_returnsNode() {
        // Escenario positivo: buscar la raíz
        // Dado: BST con raíz 20
        // Cuando: se busca 20
        // Resultado esperado: retorna nodo con dato 20
        bst.add(20);

        assertNotNull(bst.search(20));
        assertEquals(20, bst.search(20).getData());
    }

    @Test
    void testSearch_nonExistingElement_returnsNull() {
        // Escenario negativo: buscar elemento que no existe
        // Dado: BST con [20, 10, 30]
        // Cuando: se busca 99
        // Resultado esperado: retorna null
        bst.add(20);
        bst.add(10);
        bst.add(30);

        assertNull(bst.search(99));
    }

    @Test
    void testSearch_emptyBST_returnsNull() {
        // Escenario negativo: buscar en BST vacío
        // Dado: BST vacío
        // Cuando: se busca 10
        // Resultado esperado: retorna null
        assertNull(bst.search(10));
    }
}

