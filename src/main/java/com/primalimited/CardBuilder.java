package com.primalimited;

public class CardBuilder {

    public static CardBuilder Ace() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "Ace";
        builder.rankNumeric = 14;
        return builder;
    }

    public static CardBuilder King() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "King";
        builder.rankNumeric = 13;
        return builder;
    }

    public static CardBuilder Queen() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "Queen";
        builder.rankNumeric = 12;
        return builder;
    }

    public static CardBuilder Jack() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "Jack";
        builder.rankNumeric = 11;
        return builder;
    }

    public static CardBuilder Ten() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "10";
        builder.rankNumeric = 10;
        return builder;
    }

    public static CardBuilder Nine() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "9";
        builder.rankNumeric = 9;
        return builder;
    }

    public static CardBuilder Eight() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "8";
        builder.rankNumeric = 8;
        return builder;
    }

    public static CardBuilder Seven() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "7";
        builder.rankNumeric = 7;
        return builder;
    }

    public static CardBuilder Six() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "6";
        builder.rankNumeric = 6;
        return builder;
    }

    public static CardBuilder Five() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "5";
        builder.rankNumeric = 5;
        return builder;
    }

    public static CardBuilder Four() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "4";
        builder.rankNumeric = 4;
        return builder;
    }

    public static CardBuilder Three() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "3";
        builder.rankNumeric = 3;
        return builder;
    }

    public static CardBuilder Two() {
        CardBuilder builder = new CardBuilder();
        builder.rank = "2";
        builder.rankNumeric = 2;
        return builder;
    }

    private String rank;
    private int rankNumeric;
    private String suit;

    public CardBuilder() {

    }


    public Card ofHearts() {
        this.suit = "Hearts";
        return build();
    }

    public Card ofDiamonds() {
        this.suit = "Diamonds";
        return build();
    }

    public Card ofClubs() {
        this.suit = "Clubs";
        return build();
    }

    public Card ofSpades() {
        this.suit = "Spades";
        return build();
    }

    public Card build() {
        return new Card(rank, rankNumeric, suit);
    }
}
