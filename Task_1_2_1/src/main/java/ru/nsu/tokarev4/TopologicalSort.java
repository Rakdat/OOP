package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

/**
 * Класс для выполнения топологической сортировки графов.
 * Реализует алгоритм топологической сортировки на основе поиска в глубину.
 */
public class TopologicalSort {

    /**
     * Выполняет топологическую сортировку направленного ациклического графа.
     * Использует алгоритм на основе поиска в глубину с обнаружением циклов.
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     * @throws IllegalArgumentException если граф содержит циклы
     */
    public static List<Integer> sort(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Граф не может быть пустым");
        }
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> tempMarks = new HashSet<>();
        for(Integer vertex : graph.getVertices()){
            if (!visited.contains(vertex)) {
                try {
                    visit(vertex, graph, visited, tempMarks, result);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Граф содержит циклы - топологическая сортировка невозможна");
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Вспомогательный метод для рекурсивного обхода графа.
     * Реализует поиск в глубину с проверкой на циклы.
     * @param vertex текущая вершина для обработки
     * @param graph граф для обхода
     * @param visited множество посещенных вершин
     * @param tempMarks множество временно помеченных вершин (для обнаружения циклов)
     * @param result список для сохранения результата
     * @throws IllegalArgumentException если обнаружен цикл
     */
    private static void visit(int vertex, Graph graph, Set<Integer> visited, Set<Integer> tempMarks, List<Integer> result) {
        if (tempMarks.contains(vertex)) {
            throw new IllegalArgumentException("Обнаружен цикл");
        }
        if (visited.contains(vertex)) {
            return;
        }
        tempMarks.add(vertex);
        for (Integer neighbor : graph.getNeighbors(vertex)) {
            visit(neighbor, graph, visited, tempMarks, result);
        }
        tempMarks.remove(vertex);
        visited.add(vertex);
        result.add(vertex);
    }
}