package com.primalimited;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void shuffle() {
        Deck deck = new Deck();
        int freshDeckHash = deck.hashCode();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10000; i++) {
            deck = new Deck();
            deck.shuffle();
            assertNotEquals(freshDeckHash, deck.hashCode());
        }
    }
}