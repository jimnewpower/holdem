package com.primalimited;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static com.primalimited.CardBuilder.*;

class NutsTest {

    @Test
    void findNuts() {
        Nuts nuts = new Nuts();
        List<Card> board = List.of(
                Ace().ofSpades(),
                Ace().ofDiamonds(),
                Queen().ofSpades(),
                Four().ofHearts(),
                Ten().ofSpades()
        );
        Hole hole = nuts.findNuts(board);
        assertTrue(hole.contains(Jack().ofSpades()));
        assertTrue(hole.contains(King().ofSpades()));
    }

    @Test
    void k6jq10() {
        Nuts nuts = new Nuts();
        List<Card> board = List.of(
                King().ofDiamonds(),
                Six().ofSpades(),
                Jack().ofSpades(),
                Queen().ofSpades(),
                Ten().ofSpades()
        );
        Hole hole = nuts.findNuts(board);
        assertTrue(hole.contains(Ace().ofSpades()));
        assertTrue(hole.contains(King().ofSpades()));
    }

    @Test
    void a284Board() {
        List<Card> board = List.of(
                Ace().ofClubs(),
                Two().ofSpades(),
                Eight().ofSpades(),
                Four().ofHearts()
        );
        Hole hole = new Nuts().findNuts(board);
        assertEquals(3, hole.getLowestRank());
        assertEquals(5, hole.getHighestRank());
    }

    @Test
    void findAllHoleCardCombinations() {
        Nuts nuts = new Nuts();
        List<Hole> holes = nuts.findAllHoleCardCombinations(new Deck().getDeck());
        assertEquals(1326, holes.size()); // 52 choose 2 = (52 * 51) / (2 * 1) = 1326

        Deck deck = new Deck();
        deck.flop();
        holes = nuts.findAllHoleCardCombinations(deck.getDeck());
        assertEquals(1128, holes.size()); // 48 choose 2 = (48 * 47) / (2 * 1) = 1128

        deck.turn();
        holes = nuts.findAllHoleCardCombinations(deck.getDeck());
        assertEquals(1035, holes.size()); // 46 choose 2 = (46 * 45) / (2 * 1) = 1035

        deck.river();
        holes = nuts.findAllHoleCardCombinations(deck.getDeck());
        assertEquals(946, holes.size()); // 44 choose 2 = (44 * 43) / (2 * 1) = 946
    }
}