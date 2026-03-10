package ru.nsu.tokarev4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Основной класс приложения для анализа производительности проверки чисел на простоту.
 * Приложение считывает список чисел из файла и выполняет проверку тремя способами:
 * 1. Последовательно (в один поток).
 * 2. С использованием настраиваемого количества потоков (MultiThreads).
 * 3. С использованием Parallel Stream API.
 */
public class SimpleNumbers {

    /**
     * Точка входа в приложение.
     * Осуществляет чтение файла, запуск тестов и вывод результатов замеров времени.
     * @param args аргументы командной строки
     * @throws FileNotFoundException если файл с числами не найден
     */
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner in;
        String file = "";

        if (args.length == 0) {
            in = new Scanner(System.in);
            System.out.println("Введите название файла с числами:");
            file = in.nextLine();
        } else {
            for(int i = 0; i < 10000; i++) {
                numbers.add(1000003);
            }
        }

        try {
            if (args.length == 0) {
                in = new Scanner(new File(file));
                while (in.hasNextInt()) {
                    numbers.add(in.nextInt());
                }
                in.close();
                //System.out.println(file); // Можно закомментировать, чтобы не мешало питону читать цифры
            }

            // --- 1. ОДИН ПОТОК ---
            long[] oneline = new long[500];
            for (int i  = 0; i < 500; i++) {
                long startline = System.nanoTime();
                boolean line = Sequential.oneLine(numbers);
                long endline = System.nanoTime();
                oneline[i] = endline - startline;
            }
            printStats(oneline);

            // --- 2. МНОГОПОТОЧНОСТЬ ---
            int[] treadcnt = {1, 2, 4, 8, 16, 32, 64};
            for (int i : treadcnt) {
                long[] multitest = new long[500];
                for (int j = 0; j < 500; j++) {
                    long start = System.nanoTime();
                    boolean multi = MultiThreads.multiThreads(i, numbers);
                    long end = System.nanoTime();
                    multitest[j] = end - start;
                }
                printStats(multitest);
            }

            // --- 3. PARALLEL STREAM ---
            long[] paral = new long[500];
            for (int i = 0; i < 500; i++) {
                long paralstart = System.nanoTime();
                boolean paralel = ParallelStream.paralelStream(numbers);
                long paralend = System.nanoTime();
                paral[i] = paralend - paralstart;
            }
            printStats(paral);

        } catch (FileNotFoundException e){
            System.out.println("Файл не был найден");
        }
    }

    /**
     * Вспомогательный метод для расчета и вывода статистики.
     * Разбивает результаты на 10 корзин, отбрасывает 9-ю корзину (выбросы/прогрев),
     * находит самую частую среди оставшихся и считает по ней среднее.
     * Выводит результат в формате: MAX AVG MIN
     */
    private static void printStats(long[] times) {
        long absoluteMin = Arrays.stream(times).min().getAsLong();
        long absoluteMax = Arrays.stream(times).max().getAsLong();

        // Используем double для точности
        double partOfDelta = (absoluteMax - absoluteMin) / 10.0;

        long[][] parts = new long[10][2];

        long errMin = Long.MAX_VALUE;
        long errMax = Long.MIN_VALUE;

        for (long time : times) {
            int bucket;
            if (partOfDelta == 0) {
                bucket = 0;
            } else {
                bucket = (int) ((time - absoluteMin) / partOfDelta);
            }

            // Защита от выхода за границы массива
            if (bucket >= 10) {
                bucket = 9;
            }

            parts[bucket][0] += time;
            parts[bucket][1]++;

            // Если результат НЕ попал в корзину с аномально долгими тестами (9),
            // учитываем его для усов погрешности
            if (bucket != 9) {
                if (time < errMin) errMin = time;
                if (time > errMax) errMax = time;
            }
        }

        // Резерв на случай, если абсолютно все тесты улетели в 9-ю корзину
        if (errMin == Long.MAX_VALUE) errMin = absoluteMin;
        if (errMax == Long.MIN_VALUE) errMax = absoluteMax;

        // Ищем корзину с наибольшим количеством элементов СРЕДИ ОСТАВШИХСЯ (от 0 до 8)
        int bestBucketIndex = 0;
        long maxCountInBucket = -1;

        for (int i = 0; i < 9; i++) {
            if (parts[i][1] > maxCountInBucket) {
                maxCountInBucket = parts[i][1];
                bestBucketIndex = i;
            }
        }

        // Считаем среднее для самой частой корзины
        long avg = 0;
        if (parts[bestBucketIndex][1] > 0) {
            avg = parts[bestBucketIndex][0] / parts[bestBucketIndex][1];
        } else {
            avg = absoluteMin;
        }

        // Выводим результаты ровно по одной строке для каждого теста
        System.out.println(errMax + " " + avg + " " + errMin);
    }
}