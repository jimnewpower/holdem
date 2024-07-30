package com.primalimited;

import java.util.Objects;

class Card {
    private String rank;
    private int rankNumeric;
    private String suit;
    private String svgImagePath;
    private String pngImagePath;

    public Card(String rank, int rankNumeric, String suit) {
        this.rank = rank;
        this.rankNumeric = rankNumeric;
        this.suit = suit;
        this.svgImagePath = "";
        this.pngImagePath = "";
    }

    public Card(String rank, int rankNumeric, String suit, String svgImagePath, String pngImagePath) {
        this.rank = rank;
        this.rankNumeric = rankNumeric;
        this.suit = suit;
        this.svgImagePath = svgImagePath;
        this.pngImagePath = pngImagePath;
    }

    public String getRank() {
        return rank;
    }

    public int getRankNumeric() {
        return rankNumeric;
    }

    public String getSuit() {
        return suit;
    }

    public String getSvgImagePath() {
        return svgImagePath;
    }

    public String getPngImagePath() {
        return pngImagePath;
    }

    @Override
    public String toString() {
        return rank + " of " + suit + " [SVG: " + svgImagePath + ", PNG: " + pngImagePath + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(rank, card.rank) && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

