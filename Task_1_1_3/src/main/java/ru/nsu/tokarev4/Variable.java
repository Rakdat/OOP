package ru.nsu.tokarev4;

import java.util.Map;

/**
 * Класс, представляющий переменную в выражении.
 */
public class Variable implements Expression {
    private final String name;

    /**
     * Создает новую переменную.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    public String getName() {
        return name;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new IllegalArgumentException("Переменная '" + name + "' не найдена");
    }

    @Override
    public Expression derivative(String variableName) {
        if (name.equals(variableName)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}