package structures.Graph;

/**
 * implementacion de floyd-warshall que calcula los caminos mas cortos entre todos los pares de nodos en una matriz
 */
public class FloydWarshall {

    /**
     * calcula las distancias minimas
     * @param adjacencymatrix la matriz de adyacencia del grafo
     * @param size la cantidad de vertices reales
     * @return matriz con las distancias minimas entre par de nodos
     */
    public int[][] calculate(int[][] adjacencymatrix, int size) {
        int[][] dist = new int[size][size];

        // inicializar la matriz de distancias
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else if (adjacencymatrix[i][j] != 0) {
                    dist[i][j] = adjacencymatrix[i][j];
                } else {
                    dist[i][j] = 999999; // infinito para evitar desbordamiento
                }
            }
        }

        // algoritmo principal
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }
}