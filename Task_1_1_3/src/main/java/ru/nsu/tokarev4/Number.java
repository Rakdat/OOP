package ru.nsu.tokarev4;

import java.util.Map;

/**
 * Класс, представляющий числовую константу в выражении.
 * <p>Число всегда возвращает свое значение при вычислении,
 * независимо от переданных переменных.
 * <p>Производная числа всегда равна нулю.
 */
public class Number implements Expression {
    private final int value;

    /**
     * Создает новую числовую константу.
     *
     * @param value значение константы
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Возвращает значение константы.
     *
     * @return значение константы
     */
    public int getValue() {
        return value;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    @Override
    public Expression derivative(String variableName) {
        return new Number(0);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}