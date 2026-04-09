package ru.nsu.tokarev4;

import java.util.ArrayList;

/**
 * Класс-обертка для проверки списка чисел на простоту с помощью Java Parallel Stream.
 */
public class ParallelStream {

    /**
     * Статический метод для запуска параллельной проверки через создание экземпляра класса.
     * @param numbers список целых чисел
     * @return результат проверки всех чисел на простоту
     */
    public static  boolean paralelStream(ArrayList<Integer> numbers){
        return numbers.parallelStream().allMatch(SimpleOrNot::isprime);
    }
}
