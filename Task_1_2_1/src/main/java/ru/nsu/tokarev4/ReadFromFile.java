package ru.nsu.tokarev4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Утилитарный класс для чтения графов из файлов.
 * Предоставляет статический метод для загрузки графа из файла в указанном формате.
 */
public class ReadFromFile {

    /**
     * Загружает граф из файла.
     * Формат файла: каждая строка содержит две вершины, разделенные пробелом.
     * Каждая пара вершин представляет собой направленное ребро.
     * @param graph граф для загрузки данных
     * @param filename имя файла для чтения
     * @throws IOException если произошла ошибка чтения файла
     */
    public static void readGraphFromFile(Graph graph, String filename) throws IOException {
        graph.clear();

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
                        graph.addEdge(start, end);
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка в строке: " + line);
                    }
                }
            }
        }
    }
}