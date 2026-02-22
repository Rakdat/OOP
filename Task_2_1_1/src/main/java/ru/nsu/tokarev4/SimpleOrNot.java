package ru.nsu.tokarev4;

import static java.lang.Math.sqrt;


/**
 * Утилитарный класс для определения, является ли число простым.
 */
public class SimpleOrNot {

    /**
     * Проверяет, является ли переданное целое число простым.
     * Использует алгоритм проверки делителей до квадратного корня из числа.
     * @param number число для проверки
     * @return true, если число простое; false в противном случае
     */
    public static boolean checker(int number) {
        if (number <= 1){
            return false;
        }
        if (number == 2){
            return true;
        }
        for(int i = 2; i <= (int) Math.ceil(sqrt(number)); i++){
            if( number % i == 0){
                return false;
            }
        }
        return true;
    }

}
