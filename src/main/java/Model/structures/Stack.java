package Model.structures;

public class Stack <T>{


    private T[] elements;
    private int size;

    public Stack() {
        // arreglo inicial
        elements = (T[]) new Object[10];
        size = 0;
    }

    /**
     * Adds an element to the top of the stack.
     * @param element the element to add
     */
    public void push(T element) {
        if (size == elements.length) {
            resize();
        }

        elements[size] = element;
        size++;
    }

    /**
     * Removes and returns the top element of the stack.
     * @return the top element, or null if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T top = elements[size - 1];
        elements[size - 1] = null; // limpio
        size--;

        return top;
    }

    /**
     * Returns the top element without removing it.
     * @return the top element, or null if the stack is empty
     */
    public T top() {
        if (isEmpty()) {
            return null;
        }

        return elements[size - 1];
    }

    /**
     * Checks if the stack is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the stack.
     * @return current size of the stack
     */
    public int size() {
        return size;
    }

    /**
     * Resizes the internal array when capacity is reached.
     */
    private void resize() {
        // duplico el tamaño
        T[] newArray = (T[]) new Object[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
    }

}
