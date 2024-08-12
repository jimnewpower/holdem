package com.primalimited;

import org.junit.jupiter.api.Test;

import static com.primalimited.CardBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class ChenFormulaTest {

    @Test
    void testFormula() {
        // pairs
        assertEquals(20, new ChenFormula(pair(Rank.ACE)).evaluate());
        assertEquals(16, new ChenFormula(pair(Rank.KING)).evaluate());
        assertEquals(14, new ChenFormula(pair(Rank.QUEEN)).evaluate());
        assertEquals(12, new ChenFormula(pair(Rank.JACK)).evaluate());
        assertEquals(10, new ChenFormula(pair(Rank.TEN)).evaluate());
        assertEquals(9, new ChenFormula(pair(Rank.NINE)).evaluate());
        assertEquals(8, new ChenFormula(pair(Rank.EIGHT)).evaluate());
        assertEquals(7, new ChenFormula(pair(Rank.SEVEN)).evaluate());
        assertEquals(6, new ChenFormula(pair(Rank.SIX)).evaluate());
        assertEquals(5, new ChenFormula(pair(Rank.FIVE)).evaluate());
        assertEquals(5, new ChenFormula(pair(Rank.FOUR)).evaluate());
        assertEquals(5, new ChenFormula(pair(Rank.THREE)).evaluate());
        assertEquals(5, new ChenFormula(pair(Rank.TWO)).evaluate());

        // suited
        assertEquals(12, new ChenFormula(new Hole(Ace().ofClubs(), King().ofClubs())).evaluate());
        assertEquals(8, new ChenFormula(new Hole(Queen().ofClubs(), Ten().ofClubs())).evaluate());
        assertEquals(6, new ChenFormula(new Hole(Seven().ofClubs(), Five().ofClubs())).evaluate());
        assertEquals(2, new ChenFormula(new Hole(Ten().ofClubs(), Two().ofClubs())).evaluate());

        // connectors
        assertEquals(7, new ChenFormula(new Hole(Jack().ofClubs(), Ten().ofDiamonds())).evaluate());
        assertEquals(6, new ChenFormula(new Hole(Ten().ofClubs(), Nine().ofDiamonds())).evaluate());

        // others
        assertEquals(-1, new ChenFormula(new Hole(Seven().ofClubs(), Two().ofDiamonds())).evaluate());
    }

    @Test
    void testHighestCardRank() {
        assertEquals(10, new ChenFormula(new Hole(Ace().ofDiamonds(), King().ofHearts())).highestCardScore());
        assertEquals(8, new ChenFormula(new Hole(Queen().ofDiamonds(), King().ofHearts())).highestCardScore());
        assertEquals(7, new ChenFormula(new Hole(Queen().ofDiamonds(), Jack().ofHearts())).highestCardScore());
        assertEquals(6, new ChenFormula(new Hole(Ten().ofDiamonds(), Jack().ofHearts())).highestCardScore());
        assertEquals(5, new ChenFormula(new Hole(Ten().ofDiamonds(), Nine().ofHearts())).highestCardScore());
        assertEquals(4.5, new ChenFormula(new Hole(Eight().ofDiamonds(), Nine().ofHearts())).highestCardScore());
        assertEquals(4, new ChenFormula(new Hole(Eight().ofDiamonds(), Seven().ofHearts())).highestCardScore());
        assertEquals(3.5, new ChenFormula(new Hole(Six().ofDiamonds(), Seven().ofHearts())).highestCardScore());
        assertEquals(3, new ChenFormula(new Hole(Six().ofDiamonds(), Five().ofHearts())).highestCardScore());
        assertEquals(2.5, new ChenFormula(new Hole(Four().ofDiamonds(), Five().ofHearts())).highestCardScore());
        assertEquals(2, new ChenFormula(new Hole(Four().ofDiamonds(), Three().ofHearts())).highestCardScore());
        assertEquals(1.5, new ChenFormula(new Hole(Two().ofDiamonds(), Three().ofHearts())).highestCardScore());
        assertEquals(1, new ChenFormula(new Hole(Two().ofDiamonds(), Two().ofHearts())).highestCardScore());
    }

    @Test
    void testPairValues() {
        assertEquals(20, new ChenFormula(pair(Rank.ACE)).pairValue());
        assertEquals(16, new ChenFormula(pair(Rank.KING)).pairValue());
        assertEquals(14, new ChenFormula(pair(Rank.QUEEN)).pairValue());
        assertEquals(12, new ChenFormula(pair(Rank.JACK)).pairValue());
        assertEquals(10, new ChenFormula(pair(Rank.TEN)).pairValue());
        assertEquals(9, new ChenFormula(pair(Rank.NINE)).pairValue());
        assertEquals(8, new ChenFormula(pair(Rank.EIGHT)).pairValue());
        assertEquals(7, new ChenFormula(pair(Rank.SEVEN)).pairValue());
        assertEquals(6, new ChenFormula(pair(Rank.SIX)).pairValue());
        assertEquals(5, new ChenFormula(pair(Rank.FIVE)).pairValue());
        assertEquals(5, new ChenFormula(pair(Rank.FOUR)).pairValue());
        assertEquals(5, new ChenFormula(pair(Rank.THREE)).pairValue());
        assertEquals(5, new ChenFormula(pair(Rank.TWO)).pairValue());
    }

    @Test
    void testGapPenalty() {
        assertEquals(0, new ChenFormula(new Hole(Ace().ofClubs(), Ace().ofDiamonds())).gapPenalty());
        assertEquals(0, new ChenFormula(new Hole(Ace().ofClubs(), King().ofDiamonds())).gapPenalty());
        assertEquals(1, new ChenFormula(new Hole(Ace().ofClubs(), Queen().ofDiamonds())).gapPenalty());
        assertEquals(2, new ChenFormula(new Hole(Ace().ofClubs(), Jack().ofDiamonds())).gapPenalty());
        assertEquals(4, new ChenFormula(new Hole(Ace().ofClubs(), Ten().ofDiamonds())).gapPenalty());
        assertEquals(5, new ChenFormula(new Hole(Ace().ofClubs(), Nine().ofDiamonds())).gapPenalty());
    }

    private Hole pair(Rank rank) {
        switch (rank) {
            case ACE -> {
                return new Hole(Ace().ofSpades(), Ace().ofDiamonds());
            }
            case KING -> {
                return new Hole(King().ofSpades(), King().ofDiamonds());
            }
            case QUEEN -> {
                return new Hole(Queen().ofSpades(), Queen().ofDiamonds());
            }
            case JACK -> {
                return new Hole(Jack().ofSpades(), Jack().ofDiamonds());
            }
            case TEN -> {
                return new Hole(Ten().ofSpades(), Ten().ofDiamonds());
            }
            case NINE -> {
                return new Hole(Nine().ofSpades(), Nine().ofDiamonds());
            }
            case EIGHT -> {
                return new Hole(Eight().ofSpades(), Eight().ofDiamonds());
            }
            case SEVEN -> {
                return new Hole(Seven().ofSpades(), Seven().ofDiamonds());
            }
            case SIX -> {
                return new Hole(Six().ofSpades(), Six().ofDiamonds());
            }
            case FIVE -> {
                return new Hole(Five().ofSpades(), Five().ofDiamonds());
            }
            case FOUR -> {
                return new Hole(Four().ofSpades(), Four().ofDiamonds());
            }
            case THREE -> {
                return new Hole(Three().ofSpades(), Three().ofDiamonds());
            }
            case TWO -> {
                return new Hole(Two().ofSpades(), Two().ofDiamonds());
            }
        }
        return null;
    }
}