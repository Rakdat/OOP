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
                 System.out.println(file);
             }

             long[] oneline = new long[10];

             for (int i  = 0; i < 100; i++) {
                 long startline = System.currentTimeMillis();
                 boolean line = InOneLine.oneLine(numbers);
                 long endline = System.currentTimeMillis();

                 oneline[i / 10] += endline - startline;
             }

             System.out.println("Среднее время 10 тестов однопоточного решения: ");
             for (int i = 0; i < 10; i++){
                 System.out.print(oneline[i]/10 + " ");
             }
             System.out.println();
             int ind = 0;
             long[][] multitest = new long[7][10];
             int[] treadcnt = {1, 2, 4, 8, 16, 32, 64};
             for (int i : treadcnt) {
                 for (int j = 0; j < 100; j++) {
                     long start = System.currentTimeMillis();
                     boolean multi = MultiThreads.multiThreads(i, numbers);
                     long end = System.currentTimeMillis();
                     multitest[(int)(Math.log((double)i)/Math.log(2))][j/10] += end - start;
                 }
                 System.out.println("\nСреднее время 10 тестов решения на " + i + " потоках: ");
                 for(int k = 0; k < 10; k++) {
                     System.out.print(multitest[ind][k]/10 + " ");
                 }
                 ind++;
             }

             System.out.println("\n");
             long[] paral = new long[10];
             for (int i = 0; i< 100; i++) {
                 long paralstart = System.currentTimeMillis();
                 boolean paralel = ParallelStream.paralelStream(numbers);
                 long paralend = System.currentTimeMillis();
                 paral[i/10] += paralend - paralstart;
             }
            System.out.println("Среднее время 10 тестов решения в ParallelStream потоках: ");
            for(int k = 0; k < 10; k++) {
                System.out.print(paral[k]/10 + " ");
            }
        }
        catch (FileNotFoundException e){
            System.out.print("Файл не был найден");
        }
    }
}