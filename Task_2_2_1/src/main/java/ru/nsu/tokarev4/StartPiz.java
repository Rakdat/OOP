package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Главный класс приложения. Запускает симуляцию работы пиццерии.
 */
public class StartPiz {

    /**
     * Точка входа в программу. Управляет консольным вводом и циклом рабочих дней.
     * @param args аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int bakersCount = validateInput("Введите количество пекарей:", in);

        int couriersCount = validateInput("Введите количество курьеров:", in);

        int storageCapacity = validateInput("Введите вместимость склада:", in);

        boolean simulateNextDay = true;
        int globalOrderId = 1;

        while (simulateNextDay) {
            System.out.println("\n--- Начинаем новый рабочий день ---");
            globalOrderId = startPizzeria(bakersCount, couriersCount, storageCapacity, globalOrderId);

            System.out.println("\nВсе заказы завершены. Пиццерия закрыта.");
            System.out.println("Смоделировать еще один день? (y/n)");
            String answer = in.nextLine();
            if (!answer.equalsIgnoreCase("y")) {
                simulateNextDay = false;
            }
        }
        System.out.println("Работа программы завершена.");
    }

    /**
     * Проверяет пользовательский ввод на корректность (число больше 0).
     * @param in сканер для чтения ввода.
     * @return корректно введенное целое число.
     */
    private static int validateInput(String notice, Scanner in) {
        System.out.println(notice);
        int value;
        do {
            try {
                value = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число");
                value = -1;
            }
        } while (value <= 0);
        return value;
    }

    /**
     * Запускает симуляцию одного рабочего дня пиццерии.
     * @param bakersCount     количество пекарей.
     * @param couriersCount   количество курьеров.
     * @param storageCapacity максимальная вместимость склада.
     * @param startOrderId    стартовый номер для генерации заказов.
     * @return номер заказа, на котором завершился день (для продолжения на следующий день).
     */
    private static int startPizzeria(int bakersCount, int couriersCount, int storageCapacity, int startOrderId) {
        CustomQueue<Order> orderQueue = new CustomQueue<>(Integer.MAX_VALUE); // Бесконечная очередь заказов
        CustomQueue<Order> storage = new CustomQueue<>(storageCapacity); // Ограниченный склад

        long startTime = System.currentTimeMillis();

        List<Thread> bakerThreads = startBakers(bakersCount, orderQueue, storage);
        List<Thread> courierThreads = startCouriers(couriersCount, startTime, storage);

        // Поток генерации заказов
        int [] currentOrderId = {startOrderId};

        Thread orderGenerator = buildOrderGenerator(startTime, orderQueue, currentOrderId);

        endWorkDay(orderGenerator, bakerThreads, storage, courierThreads);

        return currentOrderId[0]; // Возвращаем ID для следующего дня
    }

    private static void endWorkDay(Thread orderGenerator, List<Thread> bakerThreads, CustomQueue<Order> storage, List<Thread> courierThreads) {
        try {
            // Ждем завершения генерации заказов
            orderGenerator.join();

            // Ждем пока пекари доделают все заказы в очереди
            for (Thread baker : bakerThreads) {
                baker.join();
            }

            // Пекари закончили. Значит на склад больше ничего не поступит
            storage.deactivate();

            // Ждем пока курьеры развезут все, что осталось на складе
            for (Thread courier : courierThreads) {
                courier.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread buildOrderGenerator(long startTime, CustomQueue<Order> orderQueue, int [] currentOrderId) {
        Thread orderGenerator = new Thread(() -> {
            while (System.currentTimeMillis() - startTime < 14000) {
                try {
                    Order o = new Order(currentOrderId[0]++);
                    System.out.println("[" + o.getId() + "] Поступил в очередь");
                    orderQueue.put(o);
                    Thread.sleep((int) (Math.random() * 501)); // Заказы поступают каждые 0 - 500 мс
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("--- Время приема заказов окончено! Завершаем текущие ---");
            orderQueue.deactivate(); // Больше заказов не будет
        });

        orderGenerator.start();
        return orderGenerator;
    }

    private static List<Thread> startCouriers(int couriersCount, long startTime, CustomQueue<Order> storage) {
        List<Thread> courierThreads = new ArrayList<>();
        for (int i = 0; i < couriersCount; i++) {
            int trunk = 1 + (int) (Math.random() * 4);
            Thread thread = new Thread(new Courier(trunk, startTime, storage));
            thread.start();
            courierThreads.add(thread);
        }
        return courierThreads;
    }

    private static List<Thread> startBakers(int bakersCount, CustomQueue<Order> orderQueue, CustomQueue<Order> storage) {
        List<Thread> bakerThreads = new ArrayList<>();
        for (int i = 0; i < bakersCount; i++) {
            Thread thread = new Thread(new Baker(orderQueue, storage));
            thread.start();
            bakerThreads.add(thread);
        }
        return bakerThreads;
    }
}