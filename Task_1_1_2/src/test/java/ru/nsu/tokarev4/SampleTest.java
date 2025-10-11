package ru.nsu.tokarev4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.tokarev4.Blackjack.value_of_card;

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
}