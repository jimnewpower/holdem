package com.primalimited;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FlopTest {

    @Test
    void isSuited() {
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("10", 10, "Hearts");
        Card card3 = new Card("4", 4, "Hearts");
        Flop flop = new Flop(card1, card2, card3);
        assertTrue(flop.isSuited());

        card1 = new Card("Ace", 14, "Spades");
        card2 = new Card("2", 2, "Hearts");
        card3 = new Card("5", 5, "Diamonds");
        flop = new Flop(card1, card2, card3);
        assertFalse(flop.isSuited());
    }

    @Test
    void isPaired() {
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("Ace", 14, "Spades");
        Card card3 = new Card("4", 4, "Hearts");
        Flop flop = new Flop(card1, card2, card3);
        assertTrue(flop.isPaired());

        card1 = new Card("Ace", 14, "Spades");
        card2 = new Card("2", 2, "Hearts");
        card3 = new Card("5", 5, "Diamonds");
        flop = new Flop(card1, card2, card3);
        assertFalse(flop.isPaired());
    }

    @Test
    void containsAce() {
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("10", 10, "Hearts");
        Card card3 = new Card("4", 4, "Hearts");
        Flop flop = new Flop(card1, card2, card3);
        assertTrue(flop.containsAce());

        card1 = new Card("King", 13, "Spades");
        card2 = new Card("2", 2, "Hearts");
        card3 = new Card("5", 5, "Diamonds");
        flop = new Flop(card1, card2, card3);
        assertFalse(flop.containsAce());
    }

    @Test
    void possibleStraight() {
        Card card1 = new Card("Ace", 14, "Spades");
        Card card2 = new Card("10", 10, "Hearts");
        Card card3 = new Card("Jack", 11, "Diamonds");
        Flop flop = new Flop(card1, card2, card3);
        assertTrue(flop.possibleStraight());

        card1 = new Card("Ace", 14, "Spades");
        card2 = new Card("2", 2, "Hearts");
        card3 = new Card("5", 5, "Diamonds");
        flop = new Flop(card1, card2, card3);
        assertTrue(flop.possibleStraight());
    }
}