package ru.nsu.tokarev4;

/**
 * Абстрактный класс, представляющий математическое выражение.
 * Является корнем иерархии классов для работы с выражениями.
 */
public abstract class Expression {

    /**
     * Вычисляет значение выражения при заданных значениях переменных.
     * @param variables строка с означиванием переменных в формате "var1 = value1; var2 = value2; ..."
     * @return результат вычисления выражения
     */
    public abstract int eval(String variables);

    /**
     * Вычисляет производную выражения по заданной переменной.
     * @param variableName имя переменной, по которой производится дифференцирование
     * @return новое выражение, представляющее производную
     */
    public abstract Expression derivative(String variableName);

    /**
     * Печатает выражение в консоль.
     */
    public abstract void print();

    /**
     * Возвращает строковое представление выражения.
     * @return строка, представляющая выражение
     */
    @Override
    public abstract String toString();

    /**
     * Парсит строку с означиванием переменных и возвращает значение конкретной переменной.
     * @param variables строка с означиванием переменных
     * @param varName имя искомой переменной
     * @return значение переменной
     * @throws IllegalArgumentException если переменная не найдена
     */
    protected int parseVariableValue(String variables, String varName) {
        if (variables == null || variables.trim().isEmpty()) {
            throw new IllegalArgumentException("Переменные не заданы");
        }

        String[] assignments = variables.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String name = parts[0].trim();
                String value = parts[1].trim();
                if (name.equals(varName)) {
                    return Integer.parseInt(value);
                }
            }
        }
        throw new IllegalArgumentException("Переменная '" + varName + "' не найдена");
    }
}