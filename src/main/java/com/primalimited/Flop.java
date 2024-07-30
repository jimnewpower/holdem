package com.primalimited;

import java.util.ArrayList;
import java.util.List;

public class Flop {
    private List<Card> cards;
    private Card card1;
    private Card card2;
    private Card card3;

    public Flop(Card card1, Card card2, Card card3) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.cards = new ArrayList<>(3);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public Card getCard3() {
        return card3;
    }

    @Override
    public String toString() {
        return card1 + " " + card2 + " " + card3;
    }

    public boolean isSuited() {
        return card1.getSuit().equals(card2.getSuit()) && card2.getSuit().equals(card3.getSuit());
    }

    public boolean isPaired() {
        return card1.getRank().equals(card2.getRank()) || card1.getRank().equals(card3.getRank()) || card2.getRank().equals(card3.getRank());
    }

    public boolean containsAce() {
        return card1.getRank().equals("Ace") || card2.getRank().equals("Ace") || card3.getRank().equals("Ace");
    }

    public boolean possibleStraight() {
        if (isPaired()) {
            return false;
        }

        int minRank = Math.min(card1.getRankNumeric(), Math.min(card2.getRankNumeric(), card3.getRankNumeric()));
        int maxRank = Math.max(card1.getRankNumeric(), Math.max(card2.getRankNumeric(), card3.getRankNumeric()));
        if (maxRank - minRank <= 4) {
            return true;
        }

        if (containsAce()) {
            minRank = 1;
            maxRank = 1;
            for (Card card : new Card[]{card1, card2, card3}) {
                int rank = card.getRankNumeric();
                // skip the ace
                if (rank != 14) {
                    maxRank = Math.max(maxRank, rank);
                }
            }
            return maxRank - minRank <= 4;
        }

        return false;
    }

    public List<Card> getCards() {
        return cards;
    }
}
