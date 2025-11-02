package ru.nsu.tokarev4;

import java.util.Scanner;

/**
 * Основной класс программы для интерактивной работы с выражениями.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== КАЛЬКУЛЯТОР МАТЕМАТИЧЕСКИХ ВЫРАЖЕНИЙ ===");
        System.out.println("Поддерживаемые операции: +, -, *, /");
        System.out.println("Формат выражений: (a+b), (x*y), ((a+b)*(c-d))");
        System.out.println("Формат переменных: x=5; y=10");
        System.out.println();

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Вычислить выражение");
            System.out.println("2 - Найти производную");
            System.out.println("3 - Показать примеры использования");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println("До свидания!");
                break;
            }

            switch (choice) {
                case "1":
                    calculateExpression(scanner);
                    break;
                case "2":
                    calculateDerivative(scanner);
                    break;
                case "3":
                    showExamples();
                    break;
                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void calculateExpression(Scanner scanner) {
        try {
            System.out.println("\n--- ВЫЧИСЛЕНИЕ ВЫРАЖЕНИЯ ---");
            System.out.print("Введите математическое выражение: ");
            String exprStr = scanner.nextLine().trim();

            Expression expr = ExpressionParser.parse(exprStr);
            System.out.print("Распознано выражение: ");
            expr.print();
            System.out.println();

            System.out.print("Введите значения переменных: ");
            String vars = scanner.nextLine().trim();

            int result = expr.eval(vars);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println("Проверьте правильность ввода выражения и переменных.");
        }
    }

    private static void calculateDerivative(Scanner scanner) {
        try {
            System.out.println("\n--- ВЫЧИСЛЕНИЕ ПРОИЗВОДНОЙ ---");
            System.out.print("Введите математическое выражение: ");
            String exprStr = scanner.nextLine().trim();

            Expression expr = ExpressionParser.parse(exprStr);
            System.out.print("Исходное выражение: ");
            expr.print();
            System.out.println();

            System.out.print("По какой переменной найти производную?: ");
            String varName = scanner.nextLine().trim();

            Expression deriv = expr.derivative(varName);
            System.out.print("Производная: ");
            deriv.print();
            System.out.println();

            // Предлагаем вычислить значение производной
            System.out.print("Хотите вычислить значение производной? (y/n): ");
            String calcChoice = scanner.nextLine().trim();

            if (calcChoice.equalsIgnoreCase("y")) {
                System.out.print("Введите значения переменных: ");
                String vars = scanner.nextLine().trim();

                int derivValue = deriv.eval(vars);
                System.out.println("Значение производной: " + derivValue);
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println("Проверьте правильность ввода.");
        }
    }

    private static void showExamples() {
        System.out.println("\n--- ПРИМЕРЫ ИСПОЛЬЗОВАНИЯ ---");

        System.out.println("1. Простые выражения:");
        System.out.println("   (3+5)");
        System.out.println("   ((10-2)*3)");
        System.out.println("   ((20/4)+1)");

        System.out.println("\n2. Выражения с переменными:");
        System.out.println("   (x+5)");
        System.out.println("   ((x*y)+z)");
        System.out.println("   ((a+b)*(c-d))");

        System.out.println("\n3. Формат ввода переменных:");
        System.out.println("   x=5");
        System.out.println("   x=3; y=7; z=10");
        System.out.println("   a=1; b=2; c=3; d=4");

        System.out.println("\n4. Пример вычисления:");
        System.out.println("   Выражение: (3+(2*x))");
        System.out.println("   Переменные: x=5");
        System.out.println("   Результат: 13");

        System.out.println("\n5. Пример производной:");
        System.out.println("   Выражение: (x*x)");
        System.out.println("   Переменная: x");
        System.out.println("   Производная: ((1*x)+(x*1))");

        System.out.println("\nВажно: Все выражения, кроме чисел и переменных, должны быть в скобках!");
    }
}