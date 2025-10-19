package ru.nsu.tokarev4;

import java.util.Objects;
import java.util.Scanner;
import static ru.nsu.tokarev4.Deck.*;
import static ru.nsu.tokarev4.Round.*;
import static ru.nsu.tokarev4.Score.*;

/**
 * Главный класс игры Blackjack, управляющий процессом игры.
 * Содержит методы для запуска игры, управления раундами и взаимодействия с пользователем.
 */
public class Blackjack {
    /**
     * Запускает основную игровую сессию.
     * Включает настройку колоды, управление раундами и отслеживание счета.
     * Автоматически перетасовывает колоду при необходимости.
     */
    public static void game() {
        System.out.println("Сколько колод будем использовать?");
        Scanner in = new Scanner(System.in);
        while (count_of_deck < 1 ) {
            count_of_deck = in.nextInt();
            if (count_of_deck < 1) {
                System.out.println("Нужно ввести целое число больше 1");
            }
        }
        System.out.println("Дилер берет карты и перетасовывает их");
        deckCreate();
        System.out.println("Дилер начал раздачу карт..");
        String gamestatus = "y";
        while (Objects.equals(gamestatus, "y")) {
            if (52 * count_of_deck - card_index < 10 * count_of_deck) {
                System.out.println("Ох, колода подходит к концу, время перемешать");
                shuffle();
            }
            rnd();
            System.out.println("Счет: " + global_score[0] + ":" + global_score[1]);
            System.out.println("Сыграем еще раз? y/n");
            gamestatus = in.next().toLowerCase();
            in.nextLine();
        }
        System.out.println("Тогда увидимся в следующий раз, до встречи");
    }
    /**
     * Точка входа в приложение.
     * Запрашивает у пользователя подтверждение начала игры и запускает игровой процесс.
     */
    public static void main(String[] args) {
        System.out.print("Сыграем? y/n\n");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if (Objects.equals(answer, "y")) {
            System.out.println("Начнем тогда игру!");
            game();
        } else {
            System.out.print("Сыграем в другой раз тогда");
        }
    }
}