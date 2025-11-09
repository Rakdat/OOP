package ru.nsu.tokarev4;

import java.util.*;
import java.io.*;

public class GraphManager {
    private static Scanner scanner = new Scanner(System.in);
    private static Graph currentGraph = null;

    public static void main(String[] args) {
        System.out.println("=== МЕНЕДЖЕР ГРАФОВ ===");

        while (true) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> createGraph();
                case 2 -> loadFromFile();
                case 3 -> printGraph();
                case 4 -> addVertex();
                case 5 -> removeVertex();
                case 6 -> addEdge();
                case 7 -> removeEdge();
                case 8 -> showNeighbors();
                case 9 -> showGraphInfo();
                case 10 -> compareGraphs();
                case 11 -> topologicalSort();
                case 12 -> changeGraphType();
                case 0 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Создать пустой граф");
        System.out.println("2. Загрузить граф из файла");
        System.out.println("3. Вывести граф");
        System.out.println("4. Добавить вершину");
        System.out.println("5. Удалить вершину");
        System.out.println("6. Добавить ребро");
        System.out.println("7. Удалить ребро");
        System.out.println("8. Показать соседей вершины");
        System.out.println("9. Информация о графе");
        System.out.println("10. Сравнить с другим графом");
        System.out.println("11. Топологическая сортировка");
        System.out.println("12. Сменить тип графа");
        System.out.println("0. Выход");

        if (currentGraph != null) {
            System.out.println("\nТекущий граф: " + currentGraph.getClass().getSimpleName());
        } else {
            System.out.println("\nГраф не создан!");
        }
    }

    private static void createGraph() {
        System.out.println("\n=== ВЫБОР ТИПА ГРАФА ===");
        System.out.println("1. Матрица смежности");
        System.out.println("2. Список смежности");
        System.out.println("3. Матрица инцидентности");

        int type = getIntInput("Выберите тип: ");
        switch (type) {
            case 1 -> currentGraph = new AdjacencyMatrix();
            case 2 -> currentGraph = new AdjacencyList();
            case 3 -> currentGraph = new IncidenceMatrix();
            default -> {
                System.out.println("Неверный тип!");
                return;
            }
        }
        System.out.println("Создан новый граф: " + currentGraph.getClass().getSimpleName());
    }

    private static void loadFromFile() {
        if (currentGraph == null) {
            System.out.println("Сначала создайте граф!");
            return;
        }

        System.out.print("Введите имя файла: ");
        String filename = scanner.nextLine().trim();

        try {
            currentGraph.readFromFile(filename);
            System.out.println("Граф успешно загружен из файла: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static void printGraph() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }
        System.out.println("\n" + currentGraph);
    }

    private static void addVertex() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        int vertex = getIntInput("Введите номер вершины: ");
        currentGraph.addVertex(vertex);
        System.out.println("Вершина " + vertex + " добавлена.");
    }

    private static void removeVertex() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        int vertex = getIntInput("Введите номер вершины для удаления: ");
        if (currentGraph.hasVertex(vertex)) {
            currentGraph.removeVertex(vertex);
            System.out.println("Вершина " + vertex + " удалена.");
        } else {
            System.out.println("Вершина " + vertex + " не найдена.");
        }
    }

    private static void addEdge() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        int start = getIntInput("Введите начальную вершину: ");
        int end = getIntInput("Введите конечную вершину: ");
        currentGraph.addEdge(start, end);
        System.out.println("Ребро " + start + "→" + end + " добавлено.");
    }

    private static void removeEdge() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        int start = getIntInput("Введите начальную вершину: ");
        int end = getIntInput("Введите конечную вершину: ");
        if (currentGraph.hasEdge(start, end)) {
            currentGraph.removeEdge(start, end);
            System.out.println("Ребро " + start + "→" + end + " удалено.");
        } else {
            System.out.println("Ребро " + start + "→" + end + " не найдено.");
        }
    }

    private static void showNeighbors() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        int vertex = getIntInput("Введите номер вершины: ");
        if (currentGraph.hasVertex(vertex)) {
            List<Integer> neighbors = currentGraph.getNeighbors(vertex);
            System.out.println("Соседи вершины " + vertex + ": " + neighbors);
        } else {
            System.out.println("Вершина " + vertex + " не найдена.");
        }
    }

    private static void showGraphInfo() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        System.out.println("\n=== ИНФОРМАЦИЯ О ГРАФЕ ===");
        System.out.println("Тип: " + currentGraph.getClass().getSimpleName());
        System.out.println("Количество вершин: " + currentGraph.getVertexCount());
        System.out.println("Количество рёбер: " + currentGraph.getEdgeCount());
        System.out.println("Вершины: " + currentGraph.getVertices());
    }

    private static void compareGraphs() {
        if (currentGraph == null) {
            System.out.println("Текущий граф не создан!");
            return;
        }

        System.out.println("\n=== СОЗДАНИЕ ГРАФА ДЛЯ СРАВНЕНИЯ ===");
        Graph otherGraph;
        System.out.println("1. Матрица смежности");
        System.out.println("2. Список смежности");
        System.out.println("3. Матрица инцидентности");

        int type = getIntInput("Выберите тип: ");
        switch (type) {
            case 1 -> otherGraph = new AdjacencyMatrix();
            case 2 -> otherGraph = new AdjacencyList();
            case 3 -> otherGraph = new IncidenceMatrix();
            default -> {
                System.out.println("Неверный тип!");
                return;
            }
        }

        System.out.print("Введите имя файла для второго графа: ");
        String filename = scanner.nextLine().trim();

        try {
            otherGraph.readFromFile(filename);
            boolean areEqual = currentGraph.equals(otherGraph);
            System.out.println("Графы " + (areEqual ? "РАВНЫ" : "НЕ РАВНЫ"));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static void topologicalSort() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        try {
            List<Integer> sorted = TopologicalSort.sort(currentGraph);
            System.out.println("Топологический порядок: " + sorted);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void changeGraphType() {
        if (currentGraph == null) {
            System.out.println("Граф не создан!");
            return;
        }

        // Сохраняем текущий граф во временный файл
        String tempFile = "temp_graph.txt";
        try {
            saveGraphToFile(currentGraph, tempFile);

            // Создаем новый граф нужного типа
            createGraph();

            // Загружаем данные из временного файла
            currentGraph.readFromFile(tempFile);

            // Удаляем временный файл
            new File(tempFile).delete();

            System.out.println("Тип графа изменен!");
        } catch (IOException e) {
            System.out.println("Ошибка при смене типа: " + e.getMessage());
        }
    }

    private static void saveGraphToFile(Graph graph, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int v1 : graph.getVertices()) {
                for (int v2 : graph.getVertices()) {
                    if (graph.hasEdge(v1, v2)) {
                        writer.println(v1 + " " + v2);
                    }
                }
            }
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите целое число.");
            }
        }
    }
}
