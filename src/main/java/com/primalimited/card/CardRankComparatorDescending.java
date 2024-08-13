package com.primalimited.card;

import com.primalimited.card.Card;

import java.util.Comparator;

public class CardRankComparatorDescending implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
        return Integer.compare(card2.getRankNumeric(), card1.getRankNumeric());
    }
}
