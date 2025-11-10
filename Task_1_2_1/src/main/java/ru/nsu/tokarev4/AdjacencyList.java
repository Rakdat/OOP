package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Реализация графа с использованием списка смежности.
 * Хранит для каждой вершины список смежных с ней вершин.
 */
public class AdjacencyList implements Graph {

    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    /**
     * Очищает граф, удаляя все вершины и рёбра.
     */
    @Override
    public void clear() {
        adjacencyList.clear();
    }

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, ничего не делает.
     * @param vertex вершина для добавления
     */
    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Удаляет вершину из графа.
     * Также удаляет все рёбра, связанные с этой вершиной.
     * @param vertex вершина для удаления
     */
    @Override
    public void removeVertex(int vertex) {
        for (List<Integer> neighbors : adjacencyList.values()) {
            neighbors.removeIf(v -> v == vertex);
        }
        adjacencyList.remove(vertex);
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
        adjacencyList.get(startVertex).add(endVertex);
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    @Override
    public void removeEdge(int startVertex, int endVertex) {
        if (adjacencyList.containsKey(startVertex)) {
            adjacencyList.get(startVertex).removeIf(v -> v == endVertex);
        }
    }

    /**
     * Возвращает список соседей вершины (вершин, в которые ведут рёбра из данной вершины).
     * @param vertex вершина, для которой нужно найти соседей
     * @return список соседних вершин
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        return new ArrayList<>(adjacencyList.getOrDefault(vertex, new ArrayList<>()));
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
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Проверяет существование ребра между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     * @return true если ребро существует, false в противном случае
     */
    @Override
    public boolean hasEdge(int startVertex, int endVertex) {
        if (hasVertex(startVertex)){
            return adjacencyList.get(startVertex).contains(endVertex);
        }
        return false;
    }

    /**
     * Возвращает количество вершин в графе.
     * @return количество вершин
     */
    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Возвращает количество рёбер в графе.
     * @return количество рёбер
     */
    @Override
    public int getEdgeCount() {
        int cnt = 0;
        for(List<Integer> neighbors : adjacencyList.values()) {
            cnt += neighbors.size();
        }
        return cnt;
    }

    /**
     * Возвращает множество всех вершин графа.
     * @return множество вершин
     */
    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    /**
     * Сравнивает данный граф с другим объектом.
     * Два графа считаются равными, если они имеют одинаковые множества вершин и рёбер.
     * @param obj объект для сравнения
     * @return true если графы равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph)){
            return false;
        }
        Graph other = (Graph) obj;
        if (!this.getVertices().equals(other.getVertices())){
            return false;
        }
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
     * Возвращает строковое представление графа в виде списка смежности.
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Список смежности:\n");
        sb.append("Вершины: ").append(getVertices()).append("\n");

        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }
}