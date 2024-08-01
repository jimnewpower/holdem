package com.primalimited;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Hole {
    List<Card> cards;
    private Card card1;
    private Card card2;

    public Hole(Card card1, Card card2) {
        this.card1 = card1;
        this.card2 = card2;
        this.cards = List.of(card1, card2);
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    @Override
    public String toString() {
        return card1 + " " + card2;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getHighestRank() {
        return Math.max(card1.getRankNumeric(), card2.getRankNumeric());
    }

    public int getLowestRank() {
        return Math.min(card1.getRankNumeric(), card2.getRankNumeric());
    }

    public boolean contains(Card card) {
        return card1.equals(card) || card2.equals(card);
    }
}
