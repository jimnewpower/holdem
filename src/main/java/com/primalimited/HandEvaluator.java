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

        if (isFourOfAKind(cards)) {
            return HandRank.FOUR_OF_A_KIND;
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

    public int getRank(List<Card> cards) {
        HandRank handRank = evaluateHand(cards);
        switch (handRank) {
            case ROYAL_FLUSH:
                return 14;
            case STRAIGHT_FLUSH:
                return getHighestCard(cards).getRankNumeric();
            case FOUR_OF_A_KIND:
                return getFourOfAKindRank(cards);
            case FULL_HOUSE:
                return getThreeOfAKindRank(cards) + getPairRank(cards);
            case FLUSH:
                return getHighestCard(cards).getRankNumeric();
            case STRAIGHT:
                return getHighestCard(cards).getRankNumeric();
            case THREE_OF_A_KIND:
                return getThreeOfAKindRank(cards);
            case TWO_PAIR:
                return getTwoPairsHighestRank(cards);
            case ONE_PAIR:
                return getPairRank(cards);
            case HIGH_CARD:
                return getHighestCard(cards).getRankNumeric();
            default:
                throw new IllegalArgumentException("Unknown hand rank: " + handRank);
        }
    }

    private Card getHighestCard(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        Card highestCard = cards.get(0);
        for (Card card : cards) {
            if (card.getRankNumeric() > highestCard.getRankNumeric()) {
                highestCard = card;
            }
        }
        return highestCard;
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

        int pairCount = 0;
        for (int count : rankCount.values()) {
            if (count == 2) {
                pairCount++;
            }
        }

        // Check if there are exactly one pair
        return pairCount == 1;
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

    public int getTwoPairsHighestRank(List<Card> cards) {
        if (!containsTwoPairs(cards)) {
            throw new IllegalArgumentException("cards must contain two pairs");
        }

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            String rank = card.getRank();
            rankCount.put(card.getRankNumeric(), rankCount.getOrDefault(card.getRankNumeric(), 0) + 1);
        }

        // Count how many ranks appear exactly two times
        int highestRank = 0;
        for (int rank : rankCount.keySet()) {
            if (rankCount.get(rank) == 2 && rank > highestRank) {
                highestRank = rank;
            }
        }

        return highestRank;
    }

    public int getFourOfAKindRank(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }
        if (!isFourOfAKind(cards)) {
            throw new IllegalArgumentException("cards must contain four of a kind");
        }

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            rankCount.put(card.getRankNumeric(), rankCount.getOrDefault(card.getRankNumeric(), 0) + 1);
        }

        // Find the rank that appears exactly four times
        for (Map.Entry<Integer, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 4) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("cards must contain four of a kind");
    }

    public int getThreeOfAKindRank(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }
        if (!isThreeOfAKind(cards)) {
            throw new IllegalArgumentException("cards must contain three of a kind");
        }

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            rankCount.put(card.getRankNumeric(), rankCount.getOrDefault(card.getRankNumeric(), 0) + 1);
        }

        // Find the rank that appears exactly three times
        for (Map.Entry<Integer, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 3) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("cards must contain three of a kind");
    }

    public int getPairRank(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }
        if (!containsPair(cards)) {
            throw new IllegalArgumentException("cards must contain a pair");
        }

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            rankCount.put(card.getRankNumeric(), rankCount.getOrDefault(card.getRankNumeric(), 0) + 1);
        }

        // Find the rank that appears exactly twice
        for (Map.Entry<Integer, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 2) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("cards must contain a pair");
    }

    public boolean isFourOfAKind(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        return isMultiple(cards, 4);
    }

    public boolean isThreeOfAKind(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        return isMultiple(cards, 3);
    }

    public boolean isMultiple(List<Card> cards, int multiple) {
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
            if (count == multiple) {
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
