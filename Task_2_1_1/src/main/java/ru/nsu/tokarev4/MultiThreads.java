package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для реализации многопоточной проверки чисел на простоту вручную.
 * Делит исходный список на равные блоки и распределяет их между заданным количеством потоков.
 */
public class MultiThreads {

    /**
     * Глобальный массив для хранения результатов проверки каждого числа.
     */
    static boolean[] res;

    /**
     * Выполняет многопоточную проверку списка чисел.
     * @param actualThreads желаемое количество потоков
     * @param numbers список целых чисел
     * @return true, если все числа простые; false, если найдено хотя бы одно составное или выполнение прервано
     */
    public static boolean multiThreads(int actualThreads, ArrayList<Integer> numbers){

        res = new boolean[numbers.size()];
        List<Thread> threads = new ArrayList<>();

        actualThreads = Math.min(actualThreads, numbers.size());
        int block = numbers.size()/actualThreads;
        int remain = numbers.size()-block*(actualThreads-1);

        int ind = 0;

        for(int i = 0; i < actualThreads; i++){
            int start = ind;
            if(i == actualThreads - 1){
                ind += remain;
            } else {
                ind += block;
            }

            final int st = start;
            final int end = ind;
            Thread thread = new Thread(() ->{
                for (int j = st; j < end; j++){
                    res[j] = SimpleOrNot.checker(numbers.get(j));
                }
            });

            thread.start();
            threads.add(thread);
        }
        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        for(boolean b : res){
            if (!b){
                return false;
            }
        }
        return true;
    }
}
