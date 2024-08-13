package com.primalimited;

import com.primalimited.card.Card;
import org.junit.jupiter.api.Test;

import static com.primalimited.card.CardBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testEquals() {
        Card card1 = Ace().ofSpades();
        Card card2 = Ace().ofSpades();
        assertEquals(card1, card2);

        card1 = Jack().ofDiamonds();
        card2 = Jack().ofDiamonds();
        assertEquals(card1, card2);

        card1 = Queen().ofHearts();
        card2 = King().ofHearts();
        assertFalse(card1.equals(card2));

        card1 = Queen().ofSpades();
        card2 = Queen().ofHearts();
        assertFalse(card1.equals(card2));
    }
}