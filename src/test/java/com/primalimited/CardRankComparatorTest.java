package com.primalimited;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.primalimited.CardBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CardRankComparatorTest {

    @Test
    void testRanks() {
        List<Card> cards = new ArrayList<>();
        cards.add(King().ofSpades());
        cards.add(Jack().ofSpades());
        cards.add(Nine().ofSpades());
        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(new CardRankComparator());
        assertEquals(cards.get(0), sorted.get(2));
        assertEquals(cards.get(1), sorted.get(1));
        assertEquals(cards.get(2), sorted.get(0));
    }
}