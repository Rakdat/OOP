package ru.nsu.tokarev4;

/**
 * Класс для парсинга математических выражений из строки.
 */
public class ExpressionParser {

    /**
     * Парсит математическое выражение из строки.
     *
     * @param expression строка с выражением
     * @return распарсенное выражение
     * @throws IllegalArgumentException если выражение имеет неверный формат
     */
    public static Expression parse(String expression) {
        String trimmed = expression.trim();
        return parseExpression(trimmed);
    }

    private static Expression parseExpression(String expr) {
        expr = expr.trim();

        // Если выражение в скобках, убираем внешние скобки
        if (expr.startsWith("(") && expr.endsWith(")")) {
            int balance = 0;
            boolean hasOuterParentheses = true;

            for (int i = 0; i < expr.length(); i++) {
                char c = expr.charAt(i);
                if (c == '(') balance++;
                if (c == ')') balance--;

                if (balance == 0 && i < expr.length() - 1) {
                    hasOuterParentheses = false;
                    break;
                }
            }

            if (hasOuterParentheses) {
                return parseExpression(expr.substring(1, expr.length() - 1));
            }
        }

        // Ищем оператор на верхнем уровне (с учетом баланса скобок)
        int balance = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') balance++;
            if (c == ')') balance--;

            if (balance == 0) {
                if (i < expr.length() - 1) {
                    char nextChar = expr.charAt(i + 1);
                    if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
                        // Нашли оператор
                        Expression left = parseExpression(expr.substring(0, i + 1));
                        Expression right = parseExpression(expr.substring(i + 2));

                        switch (nextChar) {
                            case '+': return new Add(left, right);
                            case '-': return new Sub(left, right);
                            case '*': return new Mul(left, right);
                            case '/': return new Div(left, right);
                        }
                    }
                }
            }
        }

        // Если не нашли оператор, это число или переменная
        if (expr.matches("-?\\d+")) {
            return new Number(Integer.parseInt(expr));
        } else if (expr.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return new Variable(expr);
        } else {
            throw new IllegalArgumentException("Неверный формат выражения: " + expr);
        }
    }
}