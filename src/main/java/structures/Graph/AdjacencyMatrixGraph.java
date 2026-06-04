package structures.Graph;

import structures.LinkedList;
import structures.Iterator;
import structures.Queue;
import structures.Stack;

public class AdjacencyMatrixGraph<T extends Comparable<T>> implements Graph<T> {

    private class MatrixVertex implements Comparable<MatrixVertex> {
        T data;
        int matrixIndex;

        public MatrixVertex(T data, int matrixIndex) {
            this.data = data;
            this.matrixIndex = matrixIndex;
        }

        @Override
        public int compareTo(MatrixVertex other) {
            return this.data.compareTo(other.data);
        }
    }

    private LinkedList<MatrixVertex> vertices;
    private int[][] matrix;
    private int capacity;
    private int size;

    public AdjacencyMatrixGraph() {
        this.capacity = 10;
        this.size = 0;
        this.vertices = new LinkedList<>();
        this.matrix = new int[capacity][capacity];
    }

    @Override
    public void addVertex(T data) {
        if (!containsVertex(data)) {
            if (size == capacity) {
                resizeMatrix();
            }
            vertices.add(new MatrixVertex(data, size));
            size++;
        }
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        MatrixVertex src = findVertex(source);
        MatrixVertex dest = findVertex(destination);

        if (src != null && dest != null) {
            matrix[src.matrixIndex][dest.matrixIndex] = weight;
        }
    }

    @Override
    public boolean containsVertex(T data) {
        return findVertex(data) != null;
    }

    @Override
    public boolean containsEdge(T source, T destination) {
        MatrixVertex src = findVertex(source);
        MatrixVertex dest = findVertex(destination);

        if (src != null && dest != null) {
            return matrix[src.matrixIndex][dest.matrixIndex] != 0;
        }
        return false;
    }

    @Override
    public void removeVertex(T data) {
        // opcional para esta fase segun interfaz
    }

    @Override
    public void removeEdge(T source, T destination) {
        MatrixVertex src = findVertex(source);
        MatrixVertex dest = findVertex(destination);

        if (src != null && dest != null) {
            matrix[src.matrixIndex][dest.matrixIndex] = 0;
        }
    }

    private MatrixVertex findVertex(T data) {
        Iterator<MatrixVertex> iterator = vertices.iterator();
        while (iterator.hasNext()) {
            MatrixVertex v = iterator.next();
            if (v.data.equals(data)) {
                return v;
            }
        }
        return null;
    }

    private void resizeMatrix() {
        int newCapacity = capacity * 2;
        int[][] newMatrix = new int[newCapacity][newCapacity];

        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        matrix = newMatrix;
        capacity = newCapacity;
    }

    public LinkedList<T> bfs(T start) {
        MatrixVertex startVertex = findVertex(start);
        if (startVertex == null) {
            return null;
        }

        Queue<MatrixVertex> queue = new Queue<>();
        LinkedList<T> visited = new LinkedList<>();
        LinkedList<T> result = new LinkedList<>();

        queue.enqueue(startVertex);
        visited.add(startVertex.data);

        while (!queue.isEmpty()) {
            MatrixVertex current = queue.dequeue();
            result.add(current.data);

            int r = current.matrixIndex;
            Iterator<MatrixVertex> iterator = vertices.iterator();

            while (iterator.hasNext()) {
                MatrixVertex neighbor = iterator.next();
                int c = neighbor.matrixIndex;

                if (matrix[r][c] != 0 && !visited.containsVisited(neighbor.data)) {
                    visited.add(neighbor.data);
                    queue.enqueue(neighbor);
                }
            }
        }

        return result;
    }

    public LinkedList<T> dfs(T start) {
        MatrixVertex startVertex = findVertex(start);
        if (startVertex == null) {
            return null;
        }

        Stack<MatrixVertex> stack = new Stack<>();
        LinkedList<T> visited = new LinkedList<>();
        LinkedList<T> result = new LinkedList<>();

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            MatrixVertex current = stack.pop();

            if (!visited.containsVisited(current.data)) {
                visited.add(current.data);
                result.add(current.data);

                int r = current.matrixIndex;
                Iterator<MatrixVertex> iterator = vertices.iterator();

                while (iterator.hasNext()) {
                    MatrixVertex neighbor = iterator.next();
                    int c = neighbor.matrixIndex;

                    if (matrix[r][c] != 0 && !visited.containsVisited(neighbor.data)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return result;
    }
}