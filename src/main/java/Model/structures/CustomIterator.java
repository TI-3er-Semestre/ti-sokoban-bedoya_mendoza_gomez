package Model.structures;

/**
 * Implementación concreta de Iterator para la lista doblemente enlazada.
 * Recorre los nodos de principio a fin usando el puntero next.
 *
 * @param <E> Tipo de dato almacenado en los nodos
 */
public class CustomIterator<E> implements Iterator<E> {

    // Nodo actual
    private Node<E> current;
    private Node<E> start;
    private boolean firstPass;

    /**
     * Constructor: el cursor se posiciona en el primer nodo de la lista.
     *
     * @param first Nodo cabeza de la lista
     */
    public CustomIterator(Node<E> first) {
        this.current = first;
        this.start = first;
        this.firstPass = true;
    }

    /**
     * Indica si hay un nodo más por visitar.
     */
    @Override
    public boolean hasNext() {
        return current != null && (firstPass || current != start);
    }


    @Override
    public E next() {
        E data = current.getData();
        current = current.getNext();
        firstPass = false;
        return data;    }

    // ── Getter ────────────────────────────────────────────────────────────────

    public Node<E> getCurrent() {
        return current;
    }
}