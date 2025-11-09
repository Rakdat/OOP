package ru.nsu.tokarev4;

import java.util.*;
import java.io.*;

public class AdjacencyList implements Graph {

    private Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyList() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void clear() {
        adjacencyList.clear();
    }

    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        for (List<Integer> neighbors : adjacencyList.values()) {
            neighbors.removeIf(v -> v == vertex);
        }
        adjacencyList.remove(vertex);
    }

    @Override
    public void addEdge(int startVertex, int endVertex) {
        addVertex(startVertex);
        addVertex(endVertex);
        adjacencyList.get(startVertex).add(endVertex);
    }

    @Override
    public void removeEdge(int startVertex, int endVertex) {
        if (adjacencyList.containsKey(startVertex)) {
            adjacencyList.get(startVertex).removeIf(v -> v == endVertex);
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return new ArrayList<>(adjacencyList.getOrDefault(vertex, new ArrayList<>()));
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    try {
                        int start = Integer.parseInt(parts[0]);
                        int end = Integer.parseInt(parts[1]);
                        addEdge(start, end);
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка в строке: " + line);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasVertex(int vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(int startVertex, int endVertex) {
        if (hasVertex(startVertex)){
            return adjacencyList.get(startVertex).contains(endVertex);
        }
        return false;
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    @Override
    public int getEdgeCount() {
        int cnt = 0;
        for(List<Integer> neighbors : adjacencyList.values()) {
            cnt += neighbors.size();
        }
        return cnt;
    }

    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

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
