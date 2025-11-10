package ru.nsu.tokarev4;

import java.util.HashMap;
import java.util.Map;

/**
 * Утилитный класс для парсинга строки с переменными.
 */
public class VariableParser {

    /**
     * Парсит строку с означиванием переменных в карту.
     *
     * @param variablesStr строка в формате "var1=value1; var2=value2; ..."
     * @return карта переменных: имя → значение
     * @throws IllegalArgumentException если строка имеет неверный формат
     */
    public static Map<String, Integer> parse(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();

        if (variablesStr == null || variablesStr.trim().isEmpty()) {
            return variables;
        }

        String[] assignments = variablesStr.split(";");
        for (String assignment : assignments) {
            String trimmedAssignment = assignment.trim();
            if (trimmedAssignment.isEmpty()) {
                continue;
            }

            String[] parts = trimmedAssignment.split("=");
            if (parts.length == 2) {
                String name = parts[0].trim();
                String value = parts[1].trim();

                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Имя переменной не может быть пустым: " + assignment);
                }

                try {
                    variables.put(name, Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Некорректное значение переменной '" + name + "': " + value
                    );
                }
            } else {
                throw new IllegalArgumentException(
                        "Некорректный формат присваивания: " + assignment
                );
            }
        }

        return variables;
    }
}