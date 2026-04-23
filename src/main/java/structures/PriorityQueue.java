package structures;

public class PriorityQueue<T extends Comparable<T>> {

    private T[] data;
    private int size;
    private int capacity;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    // verifica si esta vacia
    public boolean isEmpty() {
        return size == 0;
    }

    // agrega elemento
    public void enqueue(T element) {
        if (size == capacity) {
            return;
        }

        data[size] = element;
        size++;

        // ordenar
        int i = size - 1;

        while (i > 0 && data[i].compareTo(data[i - 1]) < 0) {
            T temp = data[i];
            data[i] = data[i - 1];
            data[i - 1] = temp;
            i--;
        }
    }

    // elimina el de mayor prioridad
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T element = data[0];

        // mover todos hacia la izquierda
        for (int i = 1; i < size; i++) {
            data[i - 1] = data[i];
        }

        size--;
        return element;
    }

    public T front() {
        if (isEmpty()) return null;
        return data[0];
    }

    public int size() {
        return size;
    }
}