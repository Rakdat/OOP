package ru.nsu.tokarev4;

import static ru.nsu.tokarev4.Hands.*;
/**
 * Класс для управления счетом игры и проверки условий победы.
 * Содержит методы для определения победителя в различных ситуациях.
 */
public class Score {
    /** Глобальный счет игры: [счет игрока, счет дилера] */
    public static int[] global_score = {0,0};
    /**
     * Проверяет наличие 21 очка после начальной раздачи карт.
     * Возвращает 0 если игра завершена (кто-то выиграл или у двоих 21 очко), 1 если игра продолжается
     */
    public static int winnerSearchAfterStart() {
        if (player_score == 21 && dealer_score !=21) {
            printhands(3);
            System.out.println("Выиграл игрок");
            global_score[0]++;
            return 0;
        } else if (dealer_score == 21 && player_score !=21) {
            printhands(3);
            System.out.println("Выиграл дилер");
            global_score[1]++;
            return 0;
        }else if (dealer_score == player_score){
            if(dealer_score == 21){
                printhands(3);
                System.out.println("Ничья");
                return 0;
            }else{
                return 1;
            }
        }
        return 1;
    }
    /**
     * Проверяет, не превысил ли игрок/дилер максимальное количество очков.
     * @param score проверяемый счет
     * Возвращает 0 - больше 21, 1 - меньше 21, 2 - равно 21
     */
    public static int checkLose(int score) {
        if (score > 21) {
            return 0;
        } else if (score == 21) {
            return 2;
        } else {
            return 1;
        }
    }
    /**
     * Определяет победителя когда оба участника имеют меньше 21 очка.
     * Сравнивает счета игрока и дилера.
     */
    public static void checkwinner() {
        if (player_score > dealer_score){
            System.out.println("Игрок победил");
            global_score[0]++;
        } else if (player_score == dealer_score) {
            System.out.println("Ничья");
        } else {
            System.out.println("Дилер победил");
            global_score[1]++;
        }
    }
}