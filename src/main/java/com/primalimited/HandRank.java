package com.primalimited;

public enum HandRank {
    HIGH_CARD("High Card"),
    ONE_PAIR("One Pair"),
    TWO_PAIR("Two Pair"),
    THREE_OF_A_KIND("Three of a Kind"),
    STRAIGHT("Straight"),
    FLUSH("Flush"),
    FULL_HOUSE("Full House"),
    FOUR_OF_A_KIND("Four of a Kind"),
    STRAIGHT_FLUSH("Straight Flush"),
    ROYAL_FLUSH("Royal Flush");

    private final String rankName;

    HandRank(String rankName) {
        this.rankName = rankName;
    }

    public String getRankName() {
        return rankName;
    }

    @Override
    public String toString() {
        return rankName;
    }
}
