package structures;

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

    public void enqueue(T element) {
        // si se llena lo agrando
        if (size == elements.length) {
            resize();
        }

        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
    }

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

    public T front() {
        // devuelve el primero sin sacarlo
        if (isEmpty()) {
            return null;
        }

        return elements[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

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
