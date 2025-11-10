package ru.nsu.tokarev4;

import java.util.List;
import java.util.Set;
import java.io.IOException;

/**
 * Интерфейс для представления ориентированного графа.
 * Определяет основные операции для работы с графом.
 */
public interface Graph {
    /**
     * 1. Операции с вершинами
     */

    /**
     * Добавляет вершину в граф.
     * @param vertex вершина для добавления
     */
    void addVertex(int vertex);

    /**
     * Удаляет вершину из графа.
     * @param vertex вершина для удаления
     */
    void removeVertex(int vertex);

    /**
     * 2. Операции с рёбрами
     */

    /**
     * Добавляет направленное ребро между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    void addEdge(int startVertex, int endVertex);

    /**
     * Удаляет направленное ребро между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     */
    void removeEdge(int startVertex, int endVertex);

    /**
     * 3. Получение списка всех "соседей" вершины
     */

    /**
     * Возвращает список соседей вершины.
     * @param vertex вершина, для которой нужно найти соседей
     * @return список соседних вершин
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * 4. Чтение из файла
     */

    /**
     * Загружает граф из файла.
     * @param filename имя файла для загрузки
     * @throws IOException если произошла ошибка чтения файла
     */
    void readFromFile(String filename) throws IOException;

    /**
     * 5. Другие необходимые операции
     */

    /**
     * Проверяет существование вершины в графе.
     * @param vertex вершина для проверки
     * @return true если вершина существует, false в противном случае
     */
    boolean hasVertex(int vertex);

    /**
     * Проверяет существование ребра между двумя вершинами.
     * @param startVertex начальная вершина ребра
     * @param endVertex конечная вершина ребра
     * @return true если ребро существует, false в противном случае
     */
    boolean hasEdge(int startVertex, int endVertex);

    /**
     * Возвращает количество вершин в графе.
     * @return количество вершин
     */
    int getVertexCount();

    /**
     * Возвращает количество рёбер в графе.
     * @return количество рёбер
     */
    int getEdgeCount();

    /**
     * Возвращает множество всех вершин графа.
     * @return множество вершин
     */
    Set<Integer> getVertices();

    /**
     * Очищает граф, удаляя все вершины и рёбра.
     */
    void clear();
}