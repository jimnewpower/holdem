package com.primalimited;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final int[] RANKS_NUMERIC = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    public Deck() {
        deck = new ArrayList<>();
        for (String suit : SUITS) {
            int index = 0;
            for (String rank : RANKS) {
                String svgPath = "/images/svg/" + rank + "_of_" + suit + ".svg";
                String pngPath = "/images/png/" + rank + "_of_" + suit + ".png";
                deck.add(new Card(rank, RANKS_NUMERIC[index++], suit, svgPath, pngPath));
            }
        }
    }

    public void shuffle() {
        SecureRandom random = new SecureRandom();
        Collections.shuffle(deck, random);
    }

    public List<Card> getDeck() {
        return deck;
    }

}

