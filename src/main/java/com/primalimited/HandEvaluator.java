package com.primalimited;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HandEvaluator {

    public HandRank evaluateHand(List<Card> cards) {
        if (isStraight(cards) && isFlush(cards) && containsAce(cards)) {
            return HandRank.ROYAL_FLUSH;
        }

        if (isStraight(cards) && isFlush(cards)) {
            return HandRank.STRAIGHT_FLUSH;
        }

        if (isThreeOfAKind(cards) && containsPair(cards)) {
            return HandRank.FULL_HOUSE;
        }

        if (isFlush(cards)) {
            return HandRank.FLUSH;
        }

        if (isStraight(cards)) {
            return HandRank.STRAIGHT;
        }

        if (isThreeOfAKind(cards)) {
            return HandRank.THREE_OF_A_KIND;
        }

        if (containsTwoPairs(cards)) {
            return HandRank.TWO_PAIR;
        }

        if (containsPair(cards)) {
            return HandRank.ONE_PAIR;
        }

        return HandRank.HIGH_CARD;
    }

    private boolean containsAce(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        for (Card card : cards) {
            if (card.getRank().equals("Ace")) {
                return true;
            }
        }
        return false;
    }

    public boolean containsPair(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        Map<String, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            String rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Check if any rank appears exactly two times
        for (int count : rankCount.values()) {
            if (count == 2) {
                return true;
            }
        }

        return false;
    }

    public boolean containsTwoPairs(List<Card> cards) {
        Map<String, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            String rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Count how many ranks appear exactly two times
        int pairCount = 0;
        for (int count : rankCount.values()) {
            if (count == 2) {
                pairCount++;
            }
        }

        // Check if there are exactly two pairs
        return pairCount == 2;
    }

    public boolean isThreeOfAKind(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        Map<String, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            String rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Check if any rank appears exactly three times
        for (int count : rankCount.values()) {
            if (count == 3) {
                return true;
            }
        }

        return false;
    }

    public boolean isStraight(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        int[] ranks = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            ranks[i] = cards.get(i).getRankNumeric();
        }
        java.util.Arrays.sort(ranks);
        for (int i = 0; i < ranks.length - 1; i++) {
            if (ranks[i] + 1 != ranks[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public boolean isFlush(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        String suit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }
}
