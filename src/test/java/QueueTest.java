import org.junit.jupiter.api.Test;
import structures.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    private Queue<Integer> setupStage1() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        return q;
    }

    @Test
    void testEnqueue_standard() {

        // Escenario: Cola vacía
        // Entrada: enqueue(5)
        // Resultado esperado: tamaño = 1, front = 5

        Queue<Integer> q = new Queue<>();

        q.enqueue(5);

        assertEquals(1, q.size());
    }

    @Test
    void testDequeue_standard() {
        // Escenario: Cola con elementos
        // Entrada: enqueue(1), enqueue(2), luego dequeue()
        // Resultado esperado: retorna 1 (FIFO)
        Queue<Integer> q = setupStage1();

        assertEquals(1, q.dequeue());
    }

    @Test
    void testDequeue_empty() {
        // Escenario: Cola vacía
        // Entrada: dequeue()
        // Resultado esperado: retorna null

        Queue<Integer> q = new Queue<>();

        assertNull(q.dequeue());
    }

    @Test
    void testFront() {
        // Escenario: Cola con múltiples elementos (FIFO)
        // Entrada: setupStage1 → [1, 2, 3]
        // Resultado esperado: front() retorna el primer elemento (1) sin eliminarlo

        Queue<Integer> q = setupStage1();

        assertEquals(1, q.front());
    }
}