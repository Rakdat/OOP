package ru.nsu.tokarev4;

import java.util.*;
import java.io.*;

public class AdjacencyMatrix implements Graph{
    private int[][] matrix;
    private Map<Integer, Integer> vertexToIndex;
    private List<Integer> indexToVertex;
    private int vertexCount;

    public AdjacencyMatrix() {
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new ArrayList<>();
        this.matrix = new int[0][0];
        this.vertexCount = 0;
    }

    @Override
    public void clear() {
        matrix = new int[0][0];
        vertexToIndex.clear();
        indexToVertex.clear();
        vertexCount = 0;
    }

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

    @Override
    public void addEdge(int startVertex, int endVertex) {
        addVertex(startVertex);
        addVertex(endVertex);
        int sv = vertexToIndex.get(startVertex);
        int ev = vertexToIndex.get(endVertex);
        matrix[sv][ev] = 1;
    }

    @Override
    public void removeEdge(int startVertex, int endVertex) {
        if (hasVertex(startVertex) && hasVertex(endVertex)){
            int sv = vertexToIndex.get(startVertex);
            int ev = vertexToIndex.get(endVertex);
            matrix[sv][ev] = 0;
        }
    }

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

    @Override
    public void readFromFile(String filename) throws IOException {
        clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()){
                    continue;
                }
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
        return vertexToIndex.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(int startVertex, int endVertex) {
        if (!hasVertex(startVertex) || !hasVertex(endVertex)){
            return false;
        }
        int startIndex = vertexToIndex.get(startVertex);
        int endIndex = vertexToIndex.get(endVertex);
        return matrix[startIndex][endIndex] == 1;
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

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

    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(vertexToIndex.keySet());
    }

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
