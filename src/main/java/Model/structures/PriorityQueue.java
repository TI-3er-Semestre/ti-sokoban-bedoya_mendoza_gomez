package Model.structures;

public class PriorityQueue<T extends Comparable<T>> {

    private T[] data;
    private int size;
    private int capacity;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    /**
     * Checks if the queue is empty.
     * @return true if the queue has no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inserts a new element into the queue.
     * Elements are automatically ordered by priority.
     * @param element the element to insert
     */
    public void enqueue(T element) {
        if (size == capacity) {
            return;
        }

        data[size] = element;
        size++;

        int i = size - 1;

        while (i > 0 && data[i].compareTo(data[i - 1]) < 0) {
            T temp = data[i];
            data[i] = data[i - 1];
            data[i - 1] = temp;
            i--;
        }
    }

    /**
     * Removes and returns the element with highest priority.
     * @return the element with highest priority, or null if empty
     */
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T element = data[0];

        for (int i = 1; i < size; i++) {
            data[i - 1] = data[i];
        }

        size--;
        return element;
    }

    /**
     * Returns the element with highest priority without removing it.
     * @return the first element, or null if empty
     */
    public T front() {
        if (isEmpty()) return null;
        return data[0];
    }

    /**
     * Returns the number of elements in the queue.
     * @return current size of the queue
     */
    public int size() {
        return size;
    }
}