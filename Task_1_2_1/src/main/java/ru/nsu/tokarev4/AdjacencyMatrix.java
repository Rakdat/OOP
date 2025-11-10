package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;

/**
 * Реализация графа с использованием матрицы смежности.
 * Хранит граф в виде квадратной матрицы, где matrix[i][j] = 1 означает наличие ребра из i в j.
 */
public class AdjacencyMatrix implements Graph{
    private int[][] matrix = new int[0][0];;
    private Map<Integer, Integer> vertexToIndex = new HashMap<>();;
    private List<Integer> indexToVertex = new ArrayList<>();;
    private int vertexCount = 0;

    /**
     * Очищает граф, удаляя все вершины и рёбра.
     */
    @Override
    public void clear() {
        matrix = new int[0][0];
        vertexToIndex.clear();
        indexToVertex.clear();
        vertexCount = 0;
    }

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, ничего не делает.
     * При добавлении новой вершины матрица смежности расширяется.
     * @param vertex вершина для добавления
     */
    @Override
    public void addVertex(int vertex) {
        if(!vertexToIndex.containsKey((vertex))){
            vertexToIndex.put(vertex, vertexCount);
            indexToVertex.add(vertex);
            int newsize = vertexCount + 1;
            int[][] newmatrix = new int[newsize][newsize];
            for(int i =0; i < vertexCount; i++){
                System.arraycopy(matrix[i], 0, newmatrix[i], 0, vertexCount);
            }
            this.matrix=newmatrix;
            vertexCount++;
        }

    }

    /**
     * Удаляет вершину из графа.
     * Также удаляет все рёбра, связанные с этой вершиной.
     * Матрица смежности перестраивается с уменьшенным размером.
     * @param vertex вершина для удаления
     */
    @Override
    public void removeVertex(int vertex) {
        if(!vertexToIndex.containsKey((vertex))){
            int removeind = vertexToIndex.get(vertex);
            int newsize = vertexCount - 1;
            int[][] newmatrix = new int[newsize][newsize];
            int ni =0;
            for(int i = 0; i < vertexCount; i++){
                int nj = 0;
                if (i == removeind){
                    continue;
                }
                for (int j = 0; j < vertexCount; j++){
                    if (j == removeind){
                        continue;
                    }
                    newmatrix[ni][nj] = matrix[i][j];
                    nj++;
                }
                ni++;
            }
            vertexToIndex.remove(vertex);
            indexToVertex.remove(removeind);

            Map<Integer, Integer> newVertexToIndex = new HashMap<>();
            List<Integer> newIndexToVertex = new ArrayList<>();
            for (int i = 0; i < indexToVertex.size(); i++) {
                int v = indexToVertex.get(i);
                newVertexToIndex.put(v, i);
                newIndexToVertex.add(v);
            }
            this.vertexToIndex = newVertexToIndex;
            this.indexToVertex = newIndexToVertex;
            this.matrix = newmatrix;
            this.vertexCount = newsize;
        }
    }

    /**
     * Добавляет направленное ребро между двумя вершинами.
     * Если вершины не существуют, они будут созданы.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    @Override
    public void addEdge(int startVertex, int endVertex) {
        addVertex(startVertex);
        addVertex(endVertex);
        int sv = vertexToIndex.get(startVertex);
        int ev = vertexToIndex.get(endVertex);
        matrix[sv][ev] = 1;
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    @Override
    public void removeEdge(int startVertex, int endVertex) {
        if (hasVertex(startVertex) && hasVertex(endVertex)){
            int sv = vertexToIndex.get(startVertex);
            int ev = vertexToIndex.get(endVertex);
            matrix[sv][ev] = 0;
        }
    }

    /**
     * Возвращает список соседей вершины (вершин, в которые ведут рёбра из данной вершины).
     * @param vertex вершина, для которой нужно найти соседей
     * @return список соседних вершин
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (!vertexToIndex.containsKey(vertex)){
            return neighbors;
        }
        else {
            int vertind = vertexToIndex.get(vertex);
            for (int i = 0; i < vertexCount; i++){
                if (matrix[vertind][i] == 1){
                    neighbors.add(indexToVertex.get(i));
                }
            }
        }
        return neighbors;
    }

    /**
     * Загружает граф из файла.
     * Формат файла: каждая строка содержит две вершины, разделенные пробелом.
     * @param filename имя файла для загрузки
     * @throws IOException если произошла ошибка чтения файла
     */
    @Override
    public void readFromFile(String filename) throws IOException {
        ReadFromFile.readGraphFromFile(this, filename);
    }

    /**
     * Проверяет существование вершины в графе.
     * @param vertex вершина для проверки
     * @return true если вершина существует, false в противном случае
     */
    @Override
    public boolean hasVertex(int vertex) {
        return vertexToIndex.containsKey(vertex);
    }

    /**
     * Проверяет существование ребра между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     * @return true если ребро существует, false в противном случае
     */
    @Override
    public boolean hasEdge(int startVertex, int endVertex) {
        if (!hasVertex(startVertex) || !hasVertex(endVertex)){
            return false;
        }
        int startIndex = vertexToIndex.get(startVertex);
        int endIndex = vertexToIndex.get(endVertex);
        return matrix[startIndex][endIndex] == 1;
    }

    /**
     * Возвращает количество вершин в графе.
     * @return количество вершин
     */
    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Возвращает количество рёбер в графе.
     * @return количество рёбер
     */
    @Override
    public int getEdgeCount() {
        int cnt = 0;
        for(int i = 0; i < vertexCount; i++){
            for(int j = 0; j < vertexCount; j++){
                if(matrix[i][j] == 1){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    /**
     * Возвращает множество всех вершин графа.
     * @return множество вершин
     */
    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(vertexToIndex.keySet());
    }

    /**
     * Сравнивает данный граф с другим объектом.
     * Два графа считаются равными, если они имеют одинаковые множества вершин и рёбер.
     * @param obj объект для сравнения
     * @return true если графы равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Graph)){
            return false;
        }
        Graph other = (Graph) obj;

        if (!this.getVertices().equals(other.getVertices())) return false;

        for (int v1 : getVertices()) {
            for (int v2 : getVertices()) {
                if (this.hasEdge(v1, v2) != other.hasEdge(v1, v2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Возвращает строковое представление графа в виде матрицы смежности.
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Матрица смежности:\n");
        sb.append("Вершины: ").append(getVertices()).append("\n");
        if(vertexCount == 0) {
            sb.append("Матрица пуста");
            return sb.toString();
        }
        sb.append("   ");
        for(int i = 0; i < vertexCount; i++) {
            sb.append(indexToVertex.get(i)).append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < vertexCount; i++){
            int currentVertex = indexToVertex.get(i);
            sb.append(currentVertex).append(" [");
            for (int j = 0; j < vertexCount; j++){
                int targetVertex = indexToVertex.get(j);
                int sourceIndex = vertexToIndex.get(currentVertex);
                int targetIndex = vertexToIndex.get(targetVertex);
                sb.append(matrix[sourceIndex][targetIndex]);
                if (j < vertexCount - 1) sb.append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}