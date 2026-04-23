package structures;

public class Stack <T>{


    private T[] elements;
    private int size;

    public Stack() {
        // arreglo inicial
        elements = (T[]) new Object[10];
        size = 0;
    }

    public void push(T element) {
        // si se llena se agrando
        if (size == elements.length) {
            resize();
        }

        elements[size] = element;
        size++;
    }

    public T pop() {
        // si esta vacia no hay nada
        if (isEmpty()) {
            return null;
        }

        T top = elements[size - 1];
        elements[size - 1] = null; // limpio
        size--;

        return top;
    }

    public T top() {
        // devuelve el de arriba sin sacarlo
        if (isEmpty()) {
            return null;
        }

        return elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        // duplico el tamaño
        T[] newArray = (T[]) new Object[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
    }

}
