package structures.Graph;

public interface Graph<T extends Comparable <T>>{


    void addVertex(T data);

    void addEdge(T source, T destination, int weight);

    boolean containsVertex(T data);

    boolean containsEdge(T source, T destination);

    void removeVertex(T data);

    void removeEdge(T source, T destination);
}
