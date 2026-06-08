import org.junit.jupiter.api.Test;
import Model.structures.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {

    private PriorityQueue<Integer> setupStage1() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(10);
        pq.enqueue(5);
        pq.enqueue(1);
        pq.enqueue(3);
        return pq;
    }

    @Test
    void testEnqueue_ordering() {
        // Escenario: Inserción desordenada
        // Entrada: enqueue(5), enqueue(1), enqueue(3)
        // Resultado esperado: el menor (1) queda al frente
        PriorityQueue<Integer> pq = setupStage1();

        assertEquals(1, pq.front());
    }

    @Test
    void testDequeue_standard() {
        // Escenario: Cola con prioridad
        // Entrada: enqueue(5), enqueue(1), dequeue()
        // Resultado esperado: retorna 1
        PriorityQueue<Integer> pq = setupStage1();

        assertEquals(1, pq.dequeue());
    }

    @Test
    void testDequeue_empty() {
        // Escenario: Cola vacía
        // Entrada: dequeue()
        // Resultado esperado: retorna null
        PriorityQueue<Integer> pq = new PriorityQueue<>(10);

        assertNull(pq.dequeue());
    }
}