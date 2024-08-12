package com.primalimited;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testShufflesNotEqualToFreshDeck() {
        Deck deck = new Deck();
        int freshDeckHash = deck.hashCode();
        for (int i = 0; i < 10000; i++) {
            deck = new Deck();
            deck.shuffle();
            assertNotEquals(freshDeckHash, deck.hashCode());
        }
    }

    @Test
    void testNoDuplicateShuffles() {
        Deck deck = new Deck();
        Set<LinkedList<Card>> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            deck = new Deck();
            LinkedList<Card> cards = deck.shuffle();
            assertFalse(set.contains(cards));
            set.add(cards);
        }
    }

}