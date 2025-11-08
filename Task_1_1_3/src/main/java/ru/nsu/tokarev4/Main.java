package ru.nsu.tokarev4;

import java.util.Map;
import java.util.Scanner;

/**
 * Основной класс программы для интерактивной работы с выражениями.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== КАЛЬКУЛЯТОР МАТЕМАТИЧЕСКИХ ВЫРАЖЕНИЙ ===");

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 - Вычислить выражение");
            System.out.println("2 - Найти производную");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) break;

            switch (choice) {
                case "1": calculateExpression(scanner); break;
                case "2": calculateDerivative(scanner); break;
                default: System.out.println("Неверный выбор!");
            }
        }

        scanner.close();
        System.out.println("До свидания!");
    }

    private static void calculateExpression(Scanner scanner) {
        try {
            System.out.print("Введите выражение: ");
            String exprStr = scanner.nextLine().trim();

            Expression expr = ExpressionParser.parse(exprStr);
            System.out.println("Выражение: " + expr.toString());

            System.out.print("Введите переменные: ");
            String varsStr = scanner.nextLine().trim();

            Map<String, Integer> variables = VariableParser.parse(varsStr);
            int result = expr.eval(variables);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void calculateDerivative(Scanner scanner) {
        try {
            System.out.print("Введите выражение: ");
            String exprStr = scanner.nextLine().trim();

            Expression expr = ExpressionParser.parse(exprStr);
            System.out.println("Исходное: " + expr.toString());

            System.out.print("Производная по: ");
            String varName = scanner.nextLine().trim();

            Expression deriv = expr.derivative(varName);
            System.out.println("Производная: " + deriv.toString());

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

}