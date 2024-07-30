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
                return getFlushTotalRank(cards);
            case STRAIGHT:
                return getHighestCardRankInStraight(cards);
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
        return hasFiveOrMoreSequentialValues(ranks);
    }

    public int getHighestCardRankInStraight(List<Card> cards) {
        if (!isStraight(cards)) {
            throw new IllegalArgumentException("cards must be a straight");
        }

        int[] ranks = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            ranks[i] = cards.get(i).getRankNumeric();
        }
        java.util.Arrays.sort(ranks);

        return findHighestInSequenceOfFiveOrMore(ranks);
    }

    public static boolean hasFiveOrMoreSequentialValues(int[] sortedArray) {
        if (sortedArray.length < 5) {
            return false;
        }

        int consecutiveCount = 1;

        for (int i = 1; i < sortedArray.length; i++) {
            if (sortedArray[i] == sortedArray[i - 1] + 1) {
                consecutiveCount++;
                if (consecutiveCount >= 5) {
                    return true;
                }
            } else {
                consecutiveCount = 1;
            }
        }

        return false;
    }

    public static int findHighestInSequenceOfFiveOrMore(int[] sortedArray) {
        if (sortedArray.length < 5) {
            return -1; // Not enough elements to have a sequence of 5
        }

        int consecutiveCount = 1;
        int highestValue = Integer.MIN_VALUE;

        for (int i = 1; i < sortedArray.length; i++) {
            if (sortedArray[i] == sortedArray[i - 1] + 1) {
                consecutiveCount++;
                if (consecutiveCount >= 5) {
                    highestValue = Math.max(highestValue, sortedArray[i]);
                }
            } else {
                consecutiveCount = 1;
            }
        }

        return highestValue == Integer.MIN_VALUE ? null : highestValue;
    }

    public int getFlushTotalRank(List<Card> cards) {
        if (!isFlush(cards)) {
            throw new IllegalArgumentException("cards must be a flush");
        }

        int totalRank = 0;
        Map<String, Integer> suitCount = new HashMap<>();
        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (String suit : suitCount.keySet()) {
            if (suitCount.get(suit) >= 5) {
                for (Card card : cards) {
                    if (card.getSuit().equals(suit)) {
                        totalRank += card.getRankNumeric();
                    }
                }
            }
        }
        return totalRank;
    }

    public boolean isFlush(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        Map<String, Integer> suitCount = new HashMap<>();
        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (String suit : suitCount.keySet()) {
            if (suitCount.get(suit) >= 5) {
                return true;
            }
        }

        return false;
    }
}
