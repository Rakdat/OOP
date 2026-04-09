package ru.nsu.tokarev4;

import java.util.ArrayList;

/**
 * Класс для однопоточной проверки списка чисел.
 */
public class Sequential {

    /**
     * Проверяет список чисел на простоту в текущем потоке.
     * * @param numbers список целых чисел
     * @return true, если все числа простые; false в противном случае
     */
    public static boolean oneLine(ArrayList<Integer> numbers){
        boolean fl = true;
        for (Integer number : numbers) {
            fl &= SimpleOrNot.isprime(number);
        }
        return fl;
    }
}
