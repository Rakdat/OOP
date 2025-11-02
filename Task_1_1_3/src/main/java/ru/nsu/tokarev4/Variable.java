package ru.nsu.tokarev4;

/**
 * Класс, представляющий переменную в выражении.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Создает новую переменную.
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Возвращает имя переменной.
     * @return имя переменной
     */
    public String getName() {
        return name;
    }

    @Override
    public int eval(String variables) {
        return parseVariableValue(variables, name);
    }

    @Override
    public Expression derivative(String variableName) {
        // Производная по самой себе равна 1, по другим переменным - 0
        if (name.equals(variableName)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public String toString() {
        return name;
    }
}