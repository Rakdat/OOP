package ru.nsu.tokarev4;

/**
 * Класс, представляющий операцию умножения двух выражений.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создает новое выражение умножения.
     * @param left левый операнд
     * @param right правый операнд
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает левый операнд.
     * @return левое выражение
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Возвращает правый операнд.
     * @return правое выражение
     */
    public Expression getRight() {
        return right;
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public Expression derivative(String variableName) {
        // Производная произведения: (uv)' = u'v + uv'
        return new Add(
                new Mul(left.derivative(variableName), right),
                new Mul(left, right.derivative(variableName))
        );
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }
}