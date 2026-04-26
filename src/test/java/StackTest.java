import org.junit.jupiter.api.Test;
import structures.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    private Stack<Integer> setupStage1() {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        return s;
    }

    @Test
    void testPush_standard() {
        // Escenario: Pila vacía
        // Entrada: push(10)
        // Resultado esperado: tamaño = 1, top = 10
        Stack<Integer> s = new Stack<>();
        s.push(10);

        assertEquals(1, s.size());
    }

    @Test
    void testPop_standard() {
        // Escenario: Pila con elementos
        // Entrada: push(1), push(2), luego pop()
        // Resultado esperado: retorna 2 (LIFO)
        Stack<Integer> s = setupStage1();

        assertEquals(2, s.pop());
    }

    @Test
    void testPop_empty() {
        // Escenario: Pila vacía
        // Entrada: pop()
        // Resultado esperado: retorna null
        Stack<Integer> s = new Stack<>();

        assertNull(s.pop());
    }

    @Test
    void testTop() {
        Stack<Integer> s = setupStage1();

        assertEquals(2, s.top());
    }
}