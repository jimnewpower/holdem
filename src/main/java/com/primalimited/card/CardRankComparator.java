package com.primalimited.card;

import com.primalimited.card.Card;

import java.util.Comparator;

public class CardRankComparator implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
        return Integer.compare(card1.getRankNumeric(), card2.getRankNumeric());
    }
}

