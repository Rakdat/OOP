package ru.nsu.tokarev4;

/**
 * Класс, представляющий числовую константу в выражении.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Создает новую числовую константу.
     * @param value значение константы
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Возвращает значение константы.
     * @return значение константы
     */
    public int getValue() {
        return value;
    }

    @Override
    public int eval(String variables) {
        return value;
    }

    @Override
    public Expression derivative(String variableName) {
        // Производная константы всегда равна 0
        return new Number(0);
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}