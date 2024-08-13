package com.primalimited;

import com.primalimited.card.Card;
import com.primalimited.card.CardBuilder;
import com.primalimited.card.Rank;
import com.primalimited.card.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardBuilderTest {

    @Test
    void testBuildFromChars() {
        Card card = CardBuilder.of('k', 'c');
        assertEquals(Rank.KING, card.getRank());
        assertEquals(Suit.CLUBS, card.getSuit());
    }

    @Test
    void testBuildFromString() {
        Card card = CardBuilder.of("KS");
        assertEquals(Rank.KING, card.getRank());
        assertEquals(Suit.SPADES, card.getSuit());

        card = CardBuilder.of("ad");
        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.DIAMONDS, card.getSuit());
    }
}