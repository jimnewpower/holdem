package com.primalimited;

public enum Rank {
    ACE("Ace", 14),
    KING("King", 13),
    QUEEN("Queen", 12),
    JACK("Jack", 11),
    TEN("10", 10),
    NINE("9", 9),
    EIGHT("8", 8),
    SEVEN("7", 7),
    SIX("6", 6),
    FIVE("5", 5),
    FOUR("4", 4),
    THREE("3", 3),
    TWO("2", 2);

    private String rank;
    private int rankNumeric;

    Rank(String rank, int rankNumeric) {
        this.rank = rank;
        this.rankNumeric = rankNumeric;
    }

    public String getRank() {
        return rank;
    }

    public int getRankNumeric() {
        return rankNumeric;
    }
}
