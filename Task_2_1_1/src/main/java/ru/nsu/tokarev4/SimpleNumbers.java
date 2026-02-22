package ru.nsu.tokarev4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     * * @param args аргументы командной строки (не используются)
     * @throws FileNotFoundException если файл с числами не найден
     */
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> numbers= new ArrayList<>();
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

        try{
             if (args.length == 0) {
                 in = new Scanner(new File(file));

                 while (in.hasNextInt()) {
                     numbers.add(in.nextInt());
                 }
                 in.close();
             }
             long startline = System.currentTimeMillis();
             boolean line = InOneLine.oneLine(numbers);
             long endline = System.currentTimeMillis();
             System.out.println("Однопоточное выполнение, результат: " + line + " время: " + (endline - startline) + "\n");

             int[] treadcnt = {1, 2, 4, 8, 12, 16, 32, 64};
             for (int i : treadcnt) {
                 long start = System.currentTimeMillis();
                 boolean multi = MultiThreads.multiThreads(i, numbers);
                 long end = System.currentTimeMillis();
                 System.out.println("Многопоточное выполнение на " + i + " потоках, результат: " + multi + ", время: " + (end - start));
             }

             long paralstart = System.currentTimeMillis();
             boolean paralel = ParallelStream.paralelStream(numbers);
             long paralend = System.currentTimeMillis();

             System.out.println("\nParallelStream, результат: " + paralel + ", время: " + (paralend - paralstart ));
        }
        catch (FileNotFoundException e){
            System.out.print("Файл не был найден");
        }
    }
}