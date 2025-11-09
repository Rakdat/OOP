package ru.nsu.tokarev4;

import java.util.*;
import java.io.*;

public interface Graph {
    // 1. Операции с вершинами
    void addVertex(int vertex);
    void removeVertex(int vertex);

    // 2. Операции с рёбрами
    void addEdge(int startVertex, int endVertex);
    void removeEdge(int startVertex, int endVertex);

    // 3. Получение списка всех "соседей" вершины
    List<Integer> getNeighbors(int vertex);

    // 4. Чтение из файла
    void readFromFile(String filename) throws IOException;

    // 5. Другие необходимые операции
    boolean hasVertex(int vertex);
    boolean hasEdge(int startVertex, int endVertex);
    int getVertexCount();
    int getEdgeCount();
    Set<Integer> getVertices();
    void clear();

    // Операции сравнения и вывода
    boolean equals(Object obj);
    String toString();
}