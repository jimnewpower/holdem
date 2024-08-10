package com.primalimited;

import java.util.*;

public class HandEvaluator {

    public HandRank evaluateHand(List<Card> _cards) {
        List<Card> cards = new ArrayList<>(_cards);

        if (isRoyalFlush(cards)) {
            return HandRank.ROYAL_FLUSH;
        }

        if (isStraightFlush(cards)) {
            return HandRank.STRAIGHT_FLUSH;
        }

        if (isFourOfAKind(cards)) {
            return HandRank.FOUR_OF_A_KIND;
        }

        if (containsTwoSets(cards) || (isThreeOfAKind(cards) && containsPair(cards))) {
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
                return getThreeOfAKindRank(cards).getRankNumeric()*3 + getPairRank(cards).getRankNumeric()*2;
            case FLUSH:
                return getFlushTotalRank(cards);
            case STRAIGHT:
                return getHighestCardRankInStraight(cards);
            case THREE_OF_A_KIND:
                return getThreeOfAKindRank(cards).getRankNumeric();
            case TWO_PAIR:
                return getTwoPairsHighestRank(cards);
            case ONE_PAIR:
                return getPairRank(cards).getRankNumeric();
            case HIGH_CARD:
                return getHighestCard(cards).getRankNumeric();
            default:
                throw new IllegalArgumentException("Unknown hand rank: " + handRank);
        }
    }

    public String getHandLabel(Hole hole, List<Card> board) {
        List<Card> hand = buildHand(hole, board);
        HandRank handRank = evaluateHand(hand);
        switch (handRank) {
            case STRAIGHT_FLUSH:
                return "Straight Flush";
            case FOUR_OF_A_KIND:
                return "Four of a Kind, " + getFourOfAKindRank(hand) + "s";
            case FULL_HOUSE:
                return getFullHouseDescription(hand);
            case FLUSH:
                Suit suit = getFlushSuit(hand);
                Rank rank = hole.getHighestRankForSuit(suit);
                if (rank == null) {
                    int high = getHighestCardRankInFlush(hand);
                    rank = Rank.fromInt(high);
                }
                return suit.getName() + " Flush, " + rank.getRank() + " high";
            case STRAIGHT:
                return "Straight, " + Rank.fromInt(getHighestCardRankInStraight(hand)).getRank() + " high";
            case THREE_OF_A_KIND:
                return "Three of a Kind, " + getThreeOfAKindRank(hand).getRank() + "s";
            case TWO_PAIR:
                return "Two Pair, " + Rank.fromInt(getTwoPairsHighestRank(hand)).getRank() + "s over " + Rank.fromInt(getTwoPairsLowestRank(hand)).getRank() + "s";
            case ONE_PAIR:
                return "Pair of " + ((getPairRank(hand) == Rank.SIX) ? "Sixes" : getPairRank(hand).getRank() + "s");
            case HIGH_CARD:
                return "High Card, " + getHighestCard(hand).getRank().getRank() + " high";
        }
        return handRank.toString();
    }

    private List<Card> buildHand(Hole hole, List<Card> board) {
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);
        return hand;
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
            if (card.getRank() == Rank.ACE) {
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

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            int rank = card.getRankNumeric();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        int pairCount = 0;
        for (int count : rankCount.values()) {
            if (count == 2) {
                pairCount++;
            }
        }

        // Check if there is at least one
        return pairCount >= 1;
    }

    public boolean containsTwoPairs(List<Card> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            Rank rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Count how many ranks appear exactly two times
        int pairCount = 0;
        for (int count : rankCount.values()) {
            if (count == 2) {
                pairCount++;
            }
        }

        // Check if there are at least two pairs (there could be 3 pairs with 7 cards)
        return pairCount >= 2;
    }

    public boolean containsTwoSets(List<Card> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            Rank rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Count how many ranks appear exactly three times
        int setCount = 0;
        for (int count : rankCount.values()) {
            if (count == 3) {
                setCount++;
            }
        }

        // Check if there are at least two sets (there could be 2 sets with 7 cards)
        return setCount >= 2;
    }

    public boolean isFullHouse(List<Card> cards) {
        return isThreeOfAKind(cards) && containsPair(cards);
    }

    public String getFullHouseDescription(List<Card> cards) {
        if (!isFullHouse(cards)) {
            throw new IllegalArgumentException("cards must be a full house");
        }

        Rank threeOfAKindRank = getThreeOfAKindRank(cards);
        Rank pairRank = getPairRank(cards);

        String description = "Full House, ";
        description += threeOfAKindRank.getRank() + "s full of ";
        description += pairRank.getRank() + "s";
        return description;
    }

    public int getTwoPairsHighestRank(List<Card> cards) {
        if (!containsTwoPairs(cards)) {
            throw new IllegalArgumentException("cards must contain two pairs");
        }

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            int rank = card.getRankNumeric();
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

    public int getTwoPairsLowestRank(List<Card> cards) {
        if (!containsTwoPairs(cards)) {
            throw new IllegalArgumentException("cards must contain two pairs");
        }

        Map<Rank, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            Rank rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Count how many ranks appear exactly two times
        int lowestRank = 15;
        for (Rank rank : rankCount.keySet()) {
            if (rankCount.get(rank) == 2 && rank.getRankNumeric() < lowestRank) {
                lowestRank = rank.getRankNumeric();
            }
        }

        return lowestRank;
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

    public Rank getThreeOfAKindRank(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }
        if (!isThreeOfAKind(cards)) {
            throw new IllegalArgumentException("cards must contain three of a kind");
        }

        Map<Rank, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            Rank rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Find the rank that appears exactly three times
        List<Rank> threeOfAKindRanks = new ArrayList<>();
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 3) {
                threeOfAKindRanks.add(entry.getKey());
            }
        }

        if (threeOfAKindRanks.isEmpty()) {
            throw new IllegalArgumentException("cards must contain three of a kind");
        }

        if (threeOfAKindRanks.size() == 1) {
            return threeOfAKindRanks.get(0);
        }

        // Sort the three of a kind ranks in descending order
        threeOfAKindRanks.sort((r1, r2) -> r2.getRankNumeric() - r1.getRankNumeric());
        return threeOfAKindRanks.get(0);
    }

    public Rank getPairRank(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }
        if (!containsPair(cards)) {
            throw new IllegalArgumentException("cards must contain a pair");
        }

        Map<Rank, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            Rank rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        // Find the rank that appears exactly twice
        List<Rank> pairRanks = new ArrayList<>();
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 2) {
                pairRanks.add(entry.getKey());
            }
        }

        if (pairRanks.isEmpty()) {
            throw new IllegalArgumentException("cards must contain exactly one pair");
        }

        if (pairRanks.size() == 1) {
            return pairRanks.get(0);
        }

        // Sort the pair ranks in descending order
        pairRanks.sort((r1, r2) -> r2.getRankNumeric() - r1.getRankNumeric());
        return pairRanks.get(0);
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

        Map<Integer, Integer> rankCount = new HashMap<>();

        // Count the occurrences of each rank
        for (Card card : cards) {
            int rank = card.getRankNumeric();
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

    public boolean isRoyalFlush(List<Card> _hand) {
        if (_hand.size() < 5) {
            return false; // A straight flush requires at least 5 cards
        }

        if (!isFlush(_hand)) {
            return false; // A straight flush requires all cards to be of the same suit
        }

        Suit flushSuit = getFlushSuit(_hand);

        // Sort the hand using CardRankComparator
        List<Card> hand = new ArrayList<>();
        for (Card card : _hand) {
            if (card.getSuit().equals(flushSuit)) {
                hand.add(card);
            }
        }

        Collections.sort(hand, new CardRankComparator());

        // Check for consecutive cards of the same suit
        int consecutiveCount = 1;
        for (int i = 1; i < hand.size(); i++) {
            Card currentCard = hand.get(i);
            Card previousCard = hand.get(i - 1);

            if (currentCard.getSuit().equals(previousCard.getSuit()) &&
                    currentCard.getRankNumeric() == previousCard.getRankNumeric() + 1) {
                consecutiveCount++;
                if (consecutiveCount >= 5 && currentCard.getRank() == Rank.ACE) {
                    return true;
                }
            } else {
                // Reset the count if not consecutive or different suit
                consecutiveCount = 1;
            }
        }

        return false;
    }

    public boolean isStraightFlush(List<Card> _hand) {
        if (_hand.size() < 5) {
            return false; // A straight flush requires at least 5 cards
        }

        if (!isFlush(_hand)) {
            return false; // A straight flush requires all cards to be of the same suit
        }

        Suit flushSuit = getFlushSuit(_hand);

        // Sort the hand using CardRankComparator
        List<Card> hand = new ArrayList<>();
        for (Card card : _hand) {
            if (card.getSuit().equals(flushSuit)) {
                hand.add(card);
            }
        }

        Collections.sort(hand, new CardRankComparator());

        // Check for consecutive cards of the same suit
        int consecutiveCount = 1;
        for (int i = 1; i < hand.size(); i++) {
            Card currentCard = hand.get(i);
            Card previousCard = hand.get(i - 1);

            if (currentCard.getSuit().equals(previousCard.getSuit()) &&
                    currentCard.getRankNumeric() == previousCard.getRankNumeric() + 1) {
                consecutiveCount++;
                if (consecutiveCount >= 5) {
                    return true;
                }
            } else {
                // Reset the count if not consecutive or different suit
                consecutiveCount = 1;
            }
        }

        return false;
    }

    public boolean isStraight(List<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards must not be empty");
        }

        // Sort the hand using CardRankComparator
        List<Card> hand = new ArrayList<>();
        for (Card card : cards) {
            hand.add(card);
        }

        Collections.sort(hand, new CardRankComparator());

        // Check for consecutive cards
        int consecutiveCount = 1;
        for (int i = 1; i < hand.size(); i++) {
            Card currentCard = hand.get(i);
            Card previousCard = hand.get(i - 1);

            if (currentCard.getRankNumeric() == previousCard.getRankNumeric()) {
                continue;
            }

            if (currentCard.getRankNumeric() == previousCard.getRankNumeric() + 1) {
                consecutiveCount++;
                if (consecutiveCount >= 5) {
                    return true;
                }
            } else {
                // Reset the count if not consecutive or different suit
                consecutiveCount = 1;
            }
        }

        if (containsAce(hand) && hand.get(0).getRankNumeric() == 2) {
            consecutiveCount = 1;
            for (int i = 1; i < hand.size(); i++) {
                Card currentCard = hand.get(i);
                Card previousCard = hand.get(i - 1);

                if (currentCard.getRankNumeric() == previousCard.getRankNumeric()) {
                    continue;
                }

                if (currentCard.getRankNumeric() == previousCard.getRankNumeric() + 1) {
                    consecutiveCount++;
                    if (consecutiveCount >= 4 && currentCard.getRank() == Rank.FIVE) {
                        return true;
                    }
                } else {
                    // Reset the count if not consecutive or different suit
                    consecutiveCount = 1;
                }
            }
        }

        return false;
    }

    public int getHighestCardRankInFlush(List<Card> cards) {
        if (!isFlush(cards)) {
            throw new IllegalArgumentException("cards must be a flush");
        }

        Suit suit = getFlushSuit(cards);
        int highestRank = 0;
        for (Card card : cards) {
            if (card.getSuit().equals(suit)) {
                highestRank = Math.max(highestRank, card.getRankNumeric());
            }
        }
        return highestRank;
    }

    public int getHighestCardRankInStraight(List<Card> cards) {
        if (!isStraight(cards)) {
            throw new IllegalArgumentException("cards must be a straight");
        }

        int[] ranks = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            ranks[i] = cards.get(i).getRankNumeric();
        }
        ranks = removeDuplicates(ranks);
        Arrays.sort(ranks);

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

        if (highestValue == Integer.MIN_VALUE && sortedArray[sortedArray.length - 1] == 14) {
            int[] aceLow = Arrays.copyOf(sortedArray, sortedArray.length);
            findAndReplace(aceLow, 14, 1);
            Arrays.sort(aceLow);
            return findHighestInSequenceOfFiveOrMore(aceLow);
        }

        return highestValue == Integer.MIN_VALUE ? -1 : highestValue;
    }

    private static int[] removeDuplicates(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int value : array) {
            set.add(value);
        }

        int[] result = new int[set.size()];
        int index = 0;
        for (int value : set) {
            result[index++] = value;
        }

        return result;
    }

    private static void findAndReplace(int[] array, int oldValue, int newValue) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == oldValue) {
                array[i] = newValue;
            }
        }
    }

    public int getFlushTotalRank(List<Card> cards) {
        if (!isFlush(cards)) {
            throw new IllegalArgumentException("cards must be a flush");
        }

        int totalRank = 0;
        Map<Suit, Integer> suitCount = new HashMap<>();
        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (Suit suit : suitCount.keySet()) {
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

        Map<Suit, Integer> suitCount = new HashMap<>();
        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (Suit suit : suitCount.keySet()) {
            if (suitCount.get(suit) >= 5) {
                return true;
            }
        }

        return false;
    }

    public Suit getFlushSuit(List<Card> cards) {
        if (!isFlush(cards)) {
            throw new IllegalArgumentException("cards must be a flush");
        }

        Map<Suit, Integer> suitCount = new HashMap<>();
        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (Suit suit : suitCount.keySet()) {
            if (suitCount.get(suit) >= 5) {
                return suit;
            }
        }

        return null;
    }

}
