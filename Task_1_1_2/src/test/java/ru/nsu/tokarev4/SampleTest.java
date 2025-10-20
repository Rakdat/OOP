package ru.nsu.tokarev4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.tokarev4.Deck.value_of_card;

class SampleTest {

    @Test
    void testCardCreation() {
        Card card = new Card("Туз", "Черви", 11);
        assertEquals("Туз", card.name);
        assertEquals("Черви", card.suit);
        assertEquals(11, card.value);
    }

    @Test
    void testCardValueCalculation() {
        assertEquals(10, value_of_card("Валет"));
        assertEquals(11, value_of_card("Туз"));
        assertEquals(7, value_of_card("7"));
    }

    @Test
    void testCardToString() {
        Card card = new Card("10", "Пики", 10);
        assertEquals(": 10 Пики (10)", card.toString());
    }

    @Test
    void testDeckCreation() {
        Deck.countOfDeck = 1;
        Deck.initdeck();

        assertEquals(52, Deck.deck.length);
        assertEquals("2", Deck.deck[0].name);
        assertEquals("Черви", Deck.deck[0].suit);
    }

    @Test
    void testCheckLose() {
        // score > 21
        assertEquals(0, Score.checkLose(22));
        // score == 21
        assertEquals(2, Score.checkLose(21));
        // score < 21
        assertEquals(1, Score.checkLose(20));
    }

    @Test
    void testWinnerSearchAfterStart() {
        // Игрок имеет 21, дилер нет
        Hands.player_score = 21;
        Hands.dealer_score = 18;
        assertEquals(0, Score.winnerSearchAfterStart());
        assertEquals(1, Score.global_score[0]);

        // Дилер имеет 21, игрок нет
        Score.global_score = new int[]{0, 0};
        Hands.player_score = 18;
        Hands.dealer_score = 21;
        assertEquals(0, Score.winnerSearchAfterStart());
        assertEquals(1, Score.global_score[1]);

        // Оба имеют 21
        Score.global_score = new int[]{0, 0};
        Hands.player_score = 21;
        Hands.dealer_score = 21;
        assertEquals(0, Score.winnerSearchAfterStart());

        // Никто не имеет 21
        Score.global_score = new int[]{0, 0};
        Hands.player_score = 18;
        Hands.dealer_score = 17;
        assertEquals(1, Score.winnerSearchAfterStart());
    }

    @Test
    void testCheckWinner() {
        Hands.player_score = 20;
        Hands.dealer_score = 18;
        Score.checkwinner();
        assertEquals(1, Score.global_score[0]);

        Score.global_score = new int[]{0, 0};
        Hands.player_score = 18;
        Hands.dealer_score = 20;
        Score.checkwinner();
        assertEquals(1, Score.global_score[1]);

        Score.global_score = new int[]{0, 0};
        Hands.player_score = 18;
        Hands.dealer_score = 18;
        Score.checkwinner();
        assertEquals(0, Score.global_score[0]);
        assertEquals(0, Score.global_score[1]);
    }

    @Test
    void testAceFind() {
        // Создаем тестовые карты
        Card ace11 = new Card("Туз", "Черви", 11);
        Card ace1 = new Card("Туз", "Пики", 1);
        Card king = new Card("Король", "Буби", 10);

        // Тест для игрока
        Hands.playerhand = new Card[]{ace1, king, ace11};
        Hands.count_player_cards = 3;
        Hands.player_score = 22;

        assertTrue(Hands.acefind(1));
        assertEquals(1, Hands.playerhand[0].value);
        assertEquals(12, Hands.player_score);
    }

    @Test
    void testShuffle() {
        Deck.countOfDeck = 1;
        Deck.initdeck();

        Card[] originalDeck = Deck.deck.clone();
        Deck.shuffle();

        // Проверяем, что колода перемешана (маловероятно, что порядок совпадет)
        boolean isShuffled = false;
        for (int i = 0; i < originalDeck.length; i++) {
            if (!originalDeck[i].equals(Deck.deck[i])) {
                isShuffled = true;
                break;
            }
        }
        assertTrue(isShuffled);
    }
}