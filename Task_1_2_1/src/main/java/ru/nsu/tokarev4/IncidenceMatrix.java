package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.io.IOException;

/**
 * Реализация графа с использованием матрицы инцидентности.
 * Хранит граф в виде матрицы, где строки соответствуют вершинам, а столбцы - рёбрам.
 * Для каждого ребра start→end: в столбце ставится 1 для start вершины и -1 для end вершины.
 */
public class IncidenceMatrix implements Graph {
    private final List<List<Integer>> incidmatrix = new ArrayList<>();
    private Map<Integer, Integer> vertexToIndex = new LinkedHashMap<>();
    private List<Integer> indexToVertex = new ArrayList<>();
    private int vertexCount = 0;
    private int edgeCount = 0;

    /**
     * Очищает граф, удаляя все вершины и рёбра.
     */
    @Override
    public void clear() {
        incidmatrix.clear();
        vertexToIndex.clear();
        indexToVertex.clear();
        vertexCount = 0;
        edgeCount = 0;
    }

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, ничего не делает.
     * При добавлении новой вершины добавляется строка в матрицу инцидентности.
     * @param vertex вершина для добавления
     */
    @Override
    public void addVertex(int vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            vertexToIndex.put(vertex, vertexCount);
            indexToVertex.add(vertex);
            for (List<Integer> row : incidmatrix) {
                row.add(0);
            }
            vertexCount++;
        }
    }

    /**
     * Удаляет вершину из графа.
     * Также удаляет все рёбра, связанные с этой вершиной.
     * Удаляет строку из матрицы инцидентности.
     * @param vertex вершина для удаления
     */
    @Override
    public void removeVertex(int vertex) {
        if (vertexToIndex.containsKey(vertex)) {
            int removedIndex = vertexToIndex.get(vertex);
            for (List<Integer> row : incidmatrix) {
                row.remove(removedIndex);
            }
            vertexToIndex.remove(vertex);
            indexToVertex.remove(removedIndex);
            Map<Integer, Integer> newVertexToIndex = new LinkedHashMap<>();
            List<Integer> newIndexToVertex = new ArrayList<>();
            for (int i = 0; i < indexToVertex.size(); i++) {
                int v = indexToVertex.get(i);
                newVertexToIndex.put(v, i);
                newIndexToVertex.add(v);
            }
            this.vertexToIndex = newVertexToIndex;
            this.indexToVertex = newIndexToVertex;
            this.vertexCount--;
        }
    }

    /**
     * Добавляет направленное ребро между двумя вершинами.
     * Если вершины не существуют, они будут созданы.
     * Добавляет новый столбец в матрицу инцидентности.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    @Override
    public void addEdge(int startVertex, int endVertex) {
        addVertex(startVertex);
        addVertex(endVertex);
        List<Integer> newEdge = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            newEdge.add(0);
        }
        int startIndex = vertexToIndex.get(startVertex);
        int endIndex = vertexToIndex.get(endVertex);
        newEdge.set(startIndex, 1);
        newEdge.set(endIndex, -1);
        incidmatrix.add(newEdge);
        edgeCount++;
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     * Удаляет столбец из матрицы инцидентности.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    @Override
    public void removeEdge(int startVertex, int endVertex) {
        for (int i = 0; i < incidmatrix.size(); i++){
            List<Integer> edge = incidmatrix.get(i);
            int startIndex = vertexToIndex.get(startVertex);
            int endIndex = vertexToIndex.get(endVertex);
            if (edge.get(startIndex) == 1 && edge.get(endIndex) == -1) {
                incidmatrix.remove(i);
                edgeCount--;
                break;
            }
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
        if(!vertexToIndex.containsKey(vertex)){
            return neighbors;
        }
        int vertexIndex = vertexToIndex.get(vertex);
        for(List<Integer> edge : incidmatrix) {
            if (edge.get(vertexIndex) == 1) {
                for (int i = 0; i < vertexCount; i++){
                    if (edge.get(i) == -1) {
                        neighbors.add(indexToVertex.get(i));
                        break;
                    }
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
        for(List<Integer> edge : incidmatrix) {
            if (edge.get(startIndex) == 1 && edge.get(endIndex) == -1) {
                return true;
            }
        }
        return false;
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
        return edgeCount;
    }

    /**
     * Возвращает множество всех вершин графа.
     * @return множество вершин
     */
    @Override
    public Set<Integer> getVertices() {
        return new LinkedHashSet<>(vertexToIndex.keySet());
    }

    /**
     * Сравнивает данный граф с другим объектом.
     * Два графа считаются равными, если они имеют одинаковые множества вершин и рёбер.
     * @param obj объект для сравнения
     * @return true если графы равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Graph)) return false;
        Graph other = (Graph) obj;
        if(!this.getVertices().equals(other.getVertices())) return false;
        for (int v1 : getVertices()){
            for (int v2 : getVertices()) {
                if (this.hasEdge(v1, v2) != other.hasEdge(v1, v2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Возвращает строковое представление графа в виде матрицы инцидентности.
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Матрица инцидентности:\n");
        sb.append("Вершины: ").append(getVertices()).append("\n");
        if (vertexCount == 0) {
            sb.append("Матрица пуста");
            return sb.toString();
        }
        sb.append("   ");
        for (int i = 0; i < edgeCount; i++) {
            sb.append("e").append(i).append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < vertexCount; i++) {
            sb.append(indexToVertex.get(i)).append(" [");
            for (int j = 0; j < edgeCount; j++) {
                int value = incidmatrix.get(j).get(i);
                sb.append(String.format("%2d", value));
                if (j < edgeCount - 1) sb.append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}