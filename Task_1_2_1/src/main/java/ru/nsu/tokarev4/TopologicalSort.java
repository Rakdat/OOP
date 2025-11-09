package ru.nsu.tokarev4;

import java.util.*;

public class TopologicalSort {

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
