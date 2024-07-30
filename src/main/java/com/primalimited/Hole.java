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
}
