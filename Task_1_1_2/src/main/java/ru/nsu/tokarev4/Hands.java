package ru.nsu.tokarev4;

import java.util.Objects;
import static ru.nsu.tokarev4.Deck.*;
/**
 * Класс для управления руками игрока и дилера.
 * Содержит методы для добавления карт, подсчета очков и отображения рук.
 */
public class Hands {
    /** Рука дилера (максимум 21 карта) */
    public static Card[] dealerhand = new Card[21];
    /** Рука игрока (максимум 21 карта) */
    public static Card[] playerhand = new Card[21];
    /** Количество карт в руке дилера */
    public static int count_dealers_cards = 0;
    /** Количество карт в руке игрока */
    public static int count_player_cards = 0;
    /** Счет дилера */
    public static int dealer_score = 0;
    /** Счет игрока */
    public static int player_score = 0;
    /**
     * Упрощенный метод для вывода содержимого рук игрока и дилера.
     * @param dealer режим отображения руки дилера (1-3)
     */
    public static void printhands(int dealer){
        printHand(1);
        printHand(dealer);
    }
    /**
     * Выводит содержимое указанной руки.
     * @param dp идентификатор руки: 1 - игрок, 2 - дилер (скрытая карта) или карт больше 2, 3 - дилер (все карты открыты)
     */
    public static void printHand(int dp) {
        if (dp == 1) {
            System.out.print("Ваша рука");
            for (int i = 0; i < count_player_cards; i++) {
                System.out.print(playerhand[i]+" ");
            }
            System.out.println("\\ Cумма вашей руки: " + player_score);
        }
        else {
            System.out.print("Рука дилера");
            if (dp == 3) {
                for (int i = 0; i < count_dealers_cards; i++) {
                    System.out.print(dealerhand[i] + " ");
                }
                System.out.println("\\ Cумма руки дилера: " + dealer_score + "\n");
            }
            else if (count_dealers_cards == 2) {
                System.out.println(dealerhand[0] + " <закрытая карта>" + "\n");
            }
            else {
                for (int i = 0; i < count_dealers_cards; i++) {
                    System.out.print(dealerhand[i] + " ");
                }
                System.out.println("\\ Cумма руки дилера: " + dealer_score + "\n");
            }
        }
    }
    /**
     * Добавляет карты в указанную руку.
     * @param dp идентификатор получателя: 1 - игрок, 2 - дилер
     * @param cnt_carts количество карт для добавления
     */
    public static void cartAdd(int dp, int cnt_carts){
        if(dp == 1){
            for(int i = 0; i < cnt_carts; i++) {
                System.out.println("Вы получили карту" + deck[card_index]);
                playerhand[count_player_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value);
                player_score += deck[card_index++].value;
            }
        }
        else{
            for(int i = 0; i < cnt_carts; i++) {
                if(count_dealers_cards > 2){
                    System.out.println("Дилер получил карту" + deck[card_index]);
                }
                dealerhand[count_dealers_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value);
                dealer_score += deck[card_index++].value;
            }
        }
    }
    /**
     * Ищет туз со значением 11 в указанной руке для уменьшения счета при переборе.
     * При нахождении изменяет стоимость туза с 11 на 1.
     * @param dp идентификатор руки: 1 - игрок, 2 - дилер
     * возвращает true если туз был найден и стоимость изменена, иначе false
     */
    public static boolean acefind(int dp) {
        boolean found = false;
        if (dp == 1) {
            for (int i = 0; i < count_player_cards; i++) {
                if (Objects.equals(playerhand[i].name, "Туз")) {
                    if (playerhand[i].value == 11) {
                        playerhand[i].value = 1;
                        player_score -= 10;
                        found = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < count_dealers_cards; i++) {
                if (Objects.equals(dealerhand[i].name, "Туз")) {
                    if (dealerhand[i].value == 11) {
                        dealerhand[i].value = 1;
                        dealer_score -= 10;
                        found = true;
                    }
                }
            }
        }
        return found;
    }
}