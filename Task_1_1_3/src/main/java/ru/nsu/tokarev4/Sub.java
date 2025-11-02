package ru.nsu.tokarev4;

/**
 * Класс, представляющий операцию вычитания двух выражений.
 */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создает новое выражение вычитания.
     * @param left левый операнд
     * @param right правый операнд
     */
    public Sub(Expression left, Expression right) {
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
        return left.eval(variables) - right.eval(variables);
    }

    @Override
    public Expression derivative(String variableName) {
        // Производная разности равна разности производных
        return new Sub(left.derivative(variableName), right.derivative(variableName));
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }
}