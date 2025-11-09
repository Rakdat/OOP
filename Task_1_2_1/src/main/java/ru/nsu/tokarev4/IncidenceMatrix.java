package ru.nsu.tokarev4;

import java.util.*;
import java.io.*;

public class IncidenceMatrix implements Graph {
    private List<List<Integer>> incidmatrix;
    private Map<Integer, Integer> vertexToIndex;
    private List<Integer> indexToVertex;
    private int vertexCount;
    private int edgeCount;

    public IncidenceMatrix() {
        this.incidmatrix = new ArrayList<>();
        this.vertexToIndex = new LinkedHashMap<>();
        this.indexToVertex = new ArrayList<>();
        this.vertexCount = 0;
        this.edgeCount = 0;
    }

    @Override
    public void clear() {
        incidmatrix.clear();
        vertexToIndex.clear();
        indexToVertex.clear();
        vertexCount = 0;
        edgeCount = 0;
    }

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

    @Override
    public void readFromFile(String filename) throws IOException {
        clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()){
                    continue;
                }
                String[] parts = line.split("\\s+");
                if(parts.length >= 2) {
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
        return vertexToIndex.containsKey(vertex);
    }

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

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public Set<Integer> getVertices() {
        return new LinkedHashSet<>(vertexToIndex.keySet());
    }

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
