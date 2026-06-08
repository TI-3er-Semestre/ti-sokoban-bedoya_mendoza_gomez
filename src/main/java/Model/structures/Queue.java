package Model.structures;

public class Queue<T> {


    private T[] elements;
    private int size;
    private int front;
    private int rear;

    public Queue() {
        // arreglo inicial
        elements = (T[]) new Object[10];
        size = 0;
        front = 0;
        rear = 0;
    }

    /**
     * Adds an element to the end of the queue.
     * @param element the element to add
     */
    public void enqueue(T element) {
        // si se llena lo agrando
        if (size == elements.length) {
            resize();
        }

        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
    }

    /**
     * Removes and returns the first element of the queue.
     * @return the first element, or null if the queue is empty
     */
    public T dequeue() {
        // si esta vacia no hay nada
        if (isEmpty()) {
            return null;
        }

        T value = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;

        return value;
    }

    /**
     * Returns the first element without removing it.
     * @return the first element, or null if the queue is empty
     */
    public T front() {
        // devuelve el primero sin sacarlo
        if (isEmpty()) {
            return null;
        }

        return elements[front];
    }

    /**
     * Checks if the queue is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the queue.
     * @return current size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Resizes the internal array when capacity is reached.
     */
    private void resize() {
        // creo uno mas grande
        T[] newArray = (T[]) new Object[elements.length * 2];

        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(front + i) % elements.length];
        }

        elements = newArray;
        front = 0;
        rear = size;
    }
}
