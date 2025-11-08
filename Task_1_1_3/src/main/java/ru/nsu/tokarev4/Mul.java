package ru.nsu.tokarev4;

import java.util.Map;

/**
 * Класс, представляющий операцию умножения двух выражений.
 */
public class Mul implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создает новое выражение умножения.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает левый операнд.
     *
     * @return левое выражение
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Возвращает правый операнд.
     *
     * @return правое выражение
     */
    public Expression getRight() {
        return right;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public Expression derivative(String variableName) {
        return new Add(
                new Mul(left.derivative(variableName), right),
                new Mul(left, right.derivative(variableName))
        );
    }

    @Override
    public String toString() {
        return "(" + left + "*" + right + ")";
    }
}