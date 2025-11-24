package ru.nsu.tokarev4;


import java.util.ConcurrentModificationException;
import java.util.Scanner;

/**
 * Интерактивная консоль для тестирования хеш-таблицы.
 * Предоставляет пользовательский интерфейс для выполнения операций с хеш-таблицей.
 */
public class Interactive {
    private static Scanner scanner = new Scanner(System.in);
    private static HashTable<String, Object> hashTable = new HashTable<>();

    /**
     * Главный метод приложения, запускающий интерактивную консоль.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("=== ИНТЕРАКТИВНАЯ КОНСОЛЬ ХЕШ-ТАБЛИЦЫ ===");
        System.out.println("Доступные команды:");
        printHelp();
        while (true) {
            try {
                System.out.print("\n> ");
                String command = scanner.nextLine().trim();
                if (command.isEmpty()){
                    continue;
                }
                switch (command.toLowerCase()) {
                    case "help":
                        printHelp();
                        break;
                    case "put":
                        putCommand();
                        break;
                    case "get":
                        getCommand();
                        break;
                    case "remove":
                        removeCommand();
                        break;
                    case "update":
                        updateCommand();
                        break;
                    case "contains":
                        containsCommand();
                        break;
                    case "size":
                        sizeCommand();
                        break;
                    case "empty":
                        emptyCommand();
                        break;
                    case "clear":
                        clearCommand();
                        break;
                    case "show":
                        showCommand();
                        break;
                    case "iterate":
                        iterateCommand();
                        break;
                    case "exit":
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неизвестная команда. Введите 'help' для списка команд.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Выводит справку по доступным командам консоли.
     */
    private static void printHelp() {
        System.out.println("""
                help     - показать это сообщение
                put      - добавить элемент
                get      - получить значение по ключу
                remove   - удалить элемент
                update   - обновить значение
                contains - проверить наличие ключа
                size     - показать размер таблицы
                empty    - проверить пуста ли таблица
                clear    - очистить таблицу
                show     - показать все элементы
                iterate  - проитерировать по элементам
                exit     - выйти из программы
                """);
    }

    /**
     * Обрабатывает команду добавления элемента в хеш-таблицу.
     */
    private static void putCommand() {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine();
        System.out.print("Введите значение: ");
        String value = scanner.nextLine();
        hashTable.put(key, value);
        System.out.println("Элемент добавлен: " + key + " -> " + value);
    }

    /**
     * Обрабатывает команду получения значения по ключу.
     */
    private static void getCommand() {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine();
        Object value = hashTable.get(key);
        if (value != null){
            System.out.println("Значение: " + value);
        } else {
            System.out.println("Ключ не найден: " + key);
        }
    }

    /**
     * Обрабатывает команду удаления элемента по ключу.
     */
    private static void removeCommand() {
        System.out.print("Введите ключ для удаления: ");
        String key = scanner.nextLine();
        Object removed = hashTable.remove(key);
        if(removed != null) {
            System.out.println("Удален элемент: " + key + " -> " + removed);
        } else{
            System.out.println("Ключ не найден: " + key);
        }
    }

    /**
     * Обрабатывает команду обновления значения по ключу.
     */
    private static void updateCommand() {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine();
        System.out.print("Введите новое значение: ");
        String value = scanner.nextLine();
        boolean updated = hashTable.update(key, value);
        if (updated){
            System.out.println("Значение обновлено: " + key + " -> " + value);
        } else {
            System.out.println("Ключ не найден: " + key);
        }
    }

    /**
     * Обрабатывает команду проверки наличия ключа в таблице.
     */
    private static void containsCommand() {
        System.out.print("Введите ключ для проверки: ");
        String key = scanner.nextLine();
        boolean contains = hashTable.containsKey(key);
        System.out.println("Таблица " + (contains ? "содержит" : "не содержит") + " ключ: " + key);
    }

    /**
     * Обрабатывает команду получения размера хеш-таблицы.
     */
    private static void sizeCommand() {
        System.out.println("Размер таблицы: " + hashTable.size());
    }

    /**
     * Обрабатывает команду проверки пустоты хеш-таблицы.
     */
    private static void emptyCommand() {
        System.out.println("Таблица " + (hashTable.isEmpty() ? "пуста" : "не пуста"));
    }

    /**
     * Обрабатывает команду очистки хеш-таблицы.
     */
    private static void clearCommand(){
        hashTable.clear();
        System.out.println("Таблица очищена");
    }

    /**
     * Обрабатывает команду отображения всех элементов таблицы.
     */
    private static void showCommand() {
        if(hashTable.isEmpty()) {
            System.out.println("Таблица пуста");
        } else{
            System.out.println("Содержимое таблицы: " + hashTable);
        }
    }

    /**
     * Обрабатывает команду итерирования по элементам таблицы.
     */
    private static void iterateCommand() {
        if (hashTable.isEmpty()){
            System.out.println("Таблица пуста");
            return;
        }

        System.out.println("Итерация по элементам:");
        int count = 1;
        for (HashTable.Entry<String, Object> entry : hashTable){
            System.out.println(count + ". " + entry.getKey() + " -> " + entry.getValue());
            count++;
        }

        // Тестирование ConcurrentModificationException
        System.out.print("\nПротестировать ConcurrentModificationException? (y/n): ");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("y")){
            try {
                System.out.println("Попытка удалить элемент во время итерации...");
                // Запоминаем первый ключ для удаления
                String keyToRemove = null;
                boolean first = true;

                for (HashTable.Entry<String, Object> entry : hashTable){
                    System.out.println("Обрабатываем: " + entry.getKey());
                    if (first){
                        keyToRemove = entry.getKey();
                        first = false;
                    }else {
                        hashTable.remove(keyToRemove);
                    }
                }
            }catch (ConcurrentModificationException e) {
                System.out.println("Поймано исключение: " + e.getClass().getSimpleName());
            }
        }
    }
}