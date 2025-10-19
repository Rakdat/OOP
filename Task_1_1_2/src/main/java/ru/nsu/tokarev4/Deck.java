package ru.nsu.tokarev4;

import java.util.Random;
import static ru.nsu.tokarev4.Card.*;

/**
 * Класс для управления колодой карт.
 * Отвечает за создание, перетасовку и выдачу карт.
 */
public class Deck {
    /** Массив всех карт в колоде */
    public static Card[] deck;
    /** Количество используемых колод */
    public static int count_of_deck = 0;
    /** Индекс следующей карты для выдачи */
    public static int card_index;
    /**
     * Создает и перемешивает новую колоду карт.
     * Инициализирует колоду и сбрасывает индекс выдачи карт.
     */
    public static void deckCreate(){
        initdeck();
        shuffle();
        card_index = 0;
    }
    /**
     * Определяет стоимость карты по ее названию.
     * И возвращает стоимость карты в очках
     */
    static int value_of_card(String rank) {
        switch (rank) {
            case "Валет":
            case "Дама":
            case "Король":
                return 10;
            case "Туз":
                return 11;
            default:
                return Integer.parseInt(rank);
        }
    }
    /**
     * Инициализирует колоду карт.
     * Создает указанное количество колод со всеми возможными комбинациями мастей и рангов.
     */
    public static void initdeck() {
        String[] suits = {"Черви","Буби","Пики","Крести"};
        String[] ranks = {"2", "3", "4" ,"5","6", "7", "8", "9", "10", "Валет", "Дама", "Король", "Туз"};
        int index = 0;
        deck = new Card[52*count_of_deck];
        for (int DeckNum = 0; DeckNum<count_of_deck; DeckNum++){
            for (String rank : ranks) {
                for (String suit : suits) {
                    deck[index] = new Card(rank, suit, value_of_card(rank));
                    index++;
                }
            }
        }
        card_index = 0;
    }
    /**
     * Перетасовывает колоду карт.
     * Сбрасывает индекс выдачи карт в начало колоды.
     */
    public static void shuffle() {
        Random random = new Random();
        for (int i = deck.length - 1; i > 0; i--) {
            int j = random.nextInt(i);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
        card_index = 0;
    }
}