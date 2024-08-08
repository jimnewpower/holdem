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

    public int getTotalRank() {
        return card1.getRankNumeric() + card2.getRankNumeric();
    }

    public int getHighestRank() {
        return Math.max(card1.getRankNumeric(), card2.getRankNumeric());
    }

    public int getSpread() {
        return Math.abs(card1.getRankNumeric() - card2.getRankNumeric());
    }

    public int getLowestRank() {
        return Math.min(card1.getRankNumeric(), card2.getRankNumeric());
    }

    public boolean contains(Card card) {
        return card1.equals(card) || card2.equals(card);
    }

    public boolean containsAce() {
        return card1.getRankNumeric() == 14 || card2.getRankNumeric() == 14;
    }

    public boolean containsKing() {
        return card1.getRankNumeric() == 13 || card2.getRankNumeric() == 13;
    }

    public boolean containsQueen() {
        return card1.getRankNumeric() == 12 || card2.getRankNumeric() == 12;
    }

    public boolean containsJack() {
        return card1.getRankNumeric() == 11 || card2.getRankNumeric() == 11;
    }

    public boolean containsTen() {
        return card1.getRankNumeric() == 10 || card2.getRankNumeric() == 10;
    }

    public boolean isSuited() {
        return card1.getSuit().equals(card2.getSuit());
    }

    public boolean isPair() {
        return card1.getRankNumeric() == card2.getRankNumeric();
    }

    public boolean isPlayable() {
        if (isPair())
            return true;
        if (isSuited() && getHighestRank() >= 9 && getSpread() <= 4)
            return true;
        if (containsAce())
            return true;
        if (isSuited() && (containsKing() || containsQueen() || containsJack() || containsTen()))
            return true;
        if (isSuited() && getSpread() <= 4)
            return true;
        if (getTotalRank() >= 19)
            return true;
        if (getSpread() <= 2 && getLowestRank() >= 6)
            return true;
        return false;
    }

    public Rank getHighestRankForSuit(Suit suit) {
        if (card1.getSuit().equals(suit) && card1.getRankNumeric() > card2.getRankNumeric()) {
            return card1.getRank();
        } else if (card2.getSuit().equals(suit)) {
            return card2.getRank();
        }
        return null;
    }
}
