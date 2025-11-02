package ru.nsu.tokarev4;

/**
 * Класс, представляющий операцию деления двух выражений.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создает новое выражение деления.
     * @param left левый операнд (числитель)
     * @param right правый операнд (знаменатель)
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает левый операнд (числитель).
     * @return левое выражение
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Возвращает правый операнд (знаменатель).
     * @return правое выражение
     */
    public Expression getRight() {
        return right;
    }

    @Override
    public int eval(String variables) {
        int denominator = right.eval(variables);
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return left.eval(variables) / denominator;
    }

    @Override
    public Expression derivative(String variableName) {
        // Производная частного: (u/v)' = (u'v - uv') / v^2
        return new Div(
                new Sub(
                        new Mul(left.derivative(variableName), right),
                        new Mul(left, right.derivative(variableName))
                ),
                new Mul(right, right)
        );
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("/");
        right.print();
        System.out.print(")");
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }
}