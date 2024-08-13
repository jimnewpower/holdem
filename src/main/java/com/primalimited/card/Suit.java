package com.primalimited.card;

public enum Suit {
    CLUBS("Clubs", 'C'),
    DIAMONDS("Diamonds", 'D'),
    HEARTS("Hearts", 'H'),
    SPADES("Spades", 'S');

    private final String name;

    private final char c;

    Suit(String name, char c) {
        this.name = name;
        this.c = c;
    }

    public String getName() {
        return name;
    }

    public char getChar() {
        return c;
    }

    public static Suit random() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static Suit parse(char c) {
        char upper = Character.toUpperCase(c);
        for (Suit suit : values()) {
            if (suit.c == upper) {
                return suit;
            }
        }
        return null;
    }
}
