package com.primalimited;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private List<Card> cards;

    public Board(List<Card> flop, Card turn, Card river) {
        this.cards = new ArrayList<>();
        this.cards.addAll(flop);
        this.cards.add(turn);
        this.cards.add(river);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
